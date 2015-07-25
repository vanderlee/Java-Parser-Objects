package example.internet;

import pobs.*;
import pobs.parser.*;
import pobs.utility.POBS;

/**
 * Insert the type's description here.
 * 
 * @author: Martijn W. van der Lee
 */
public class Domain extends pobs.PParser {
    private static pobs.PObject parser;
    /**
     * Email constructor comment.
     * Construct the domain parser, based on RFC821 (SMTP email address) specs
     */
    public Domain() {
        super();

        if (parser != null) {
            return; // early exit if parser is already defined elsewhere
        }

        // <snum> := one, two, or three digits representing a decimal
        //		     integer value in the range 0 through 255
        PObject snum = new PLimit(new PRepeat(POBS.digit(), 1, 3), 0, 255);

        // <let-dig-hyp> := <a> | <d> | "-"
        PObject let_dig_hyp = new POr(POBS.alphaNum(), new PChar('-'));

        // <dotnum> := <snum> "." <snum> "." <snum> "." <snum>
        //	ListLimit
        PObject dotnum =
            new PSequence(
                snum,
                new PRepeat(new PSequence(new PChar('.'), snum), 3, 3));

        // <number> := <d> | <d> <number> := <d>+
        PObject number = POBS.UNSIGNED_INT;

        // <name> := <a> <ldh-str> <let-dig>
        // Altered; POBS is non-backtracking thus ldh-str would eat any data
        // let-dig would parse
        // <name> := <a> <let_dig_hyp> ('-'? <let-dig>)+
        PObject name =
            new PSequence(
                POBS.alpha(),
                let_dig_hyp,
                new PMultiple(
                    new PSequence(new POptional(new PChar('-')), POBS.alphaNum())));

        // <element> := <name> | "#" <number> | "[" <dotnum> "]"
        PObject element =
            new POr(
                name,
                new PSequence(new PChar('#'), number),
                new PSequence(new PChar('['), dotnum, new PChar(']')));

        // <domain> := <element> | <element> "." <domain>
        //			:= <element> ("." <element>)*
        //	ListLimit > List
        PObject domain =
            new PSequence(
                element,
                new PKleene(new PSequence(new PChar('.'), element)));

        // switchpoint
        Domain.parser = domain;
    }
    /**
     * Starts the application.
     * @param args an array of command-line arguments
     */
    public static void main(java.lang.String[] args) {
        Domain d = new Domain();

        System.out.println(d.process("vanderlee"));
        System.out.println(d.process("www.v-d-l.com"));
    }
    /**
     * @see pobs.PObject
     */
    public boolean process(java.lang.String input) {
        pobs.PMatch m =
            parser.process(
                new pobs.scanner.PStringScanner(input),
                0,
                new pobs.PContext());
        return (m.isMatch() && m.getLength() == input.length());
    }
    /**
     * @see pobs.PObject
     */
    public pobs.PMatch parse(
        pobs.PScanner input,
        long begin,
        pobs.PContext context) {
        return parser.process(input, begin, context);
    }
}
