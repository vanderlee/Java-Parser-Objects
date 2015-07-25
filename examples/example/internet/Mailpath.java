package example.internet;

import pobs.*;
import pobs.parser.*;

/**
 * Insert the type's description here.
 * 
 * @author: Martijn W. van der Lee
 */
public class Mailpath extends pobs.PParser {
    private static pobs.PObject parser;

    /**
     * Email constructor comment.
     */
    public Mailpath() {
        super();

        // domain := {Domain}
        PObject domain = new example.internet.Domain();

        // mailbox := {Mailbox}
        PObject mailbox = new example.internet.Mailbox();

        // at-domain := "@" domain
        PObject at_domain = new PSequence(new PChar('@'), domain);

        // a-d-l := at-domain | at-domain "," a-d-l := at-domain ('.' at-domain)+
        PObject a_d_l =
            new PSequence(
                at_domain,
                new PKleene(new PSequence(new PChar(','), at_domain)));

        // path := "&lt" [ a-d-l ":" ] mailbox ">" := '&lt' (a-d-l ":")* mailbox '>'
        PObject path =
            new PSequence(
                new PChar('<'),
                new PKleene(new PSequence(a_d_l, new PChar(':'))),
                mailbox,
                new PChar('>'));

        parser = path;
    }
    /**
     * Starts the application.
     * @param args an array of command-line arguments
     */
    public static void main(java.lang.String[] args) {
        Mailbox e = new Mailbox();

        System.out.println(e.process("martijn@vanderlee.com"));
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
