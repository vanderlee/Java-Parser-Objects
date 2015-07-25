package example.internet;

import pobs.*;
import pobs.parser.*;

/**
 * Insert the type's description here.
 * 
 * @author: Martijn W. van der Lee
 */
public class Mailbox extends pobs.PParser {
    private static pobs.PObject parser;
    /**
     * Email constructor comment.
     */
    public Mailbox() {
        super();

        // x := any one of the 128 ASCII characters (no exceptions)
        PObject x = new PRange("\u0000-\u007F");

        //  <special>       := "<" | ">" | "(" | ")" | "[" | "]" | "\" | "."
        //                      | "," | ";" | ":" | "@"  """ | the control
        //                      characters (ASCII codes 0 through 31 inclusive and
        //                      127)
        PObject special = new PRange("<>()[]\\.,;:@\"\u0000-\u001F\u007F");

        //  <SP>            := the space character (ASCII code 32)
        PObject sp = new PChar(' ');

        //  <c>             := any one of the 128 ASCII characters, but not any
        //                      <special> or <SP>
        PObject c = new PExcept(x, new POr(special, sp));

        //  <q>             := any one of the 128 ASCII characters except <CR>,
        //                      <LF>, quote ("), or backslash (\)
        PObject q = new PExcept(x, new PSet("\r\n\"\\"));

        // char := c | "\" x
        PObject character = new POr(c, new PSequence(new PChar('\\'), x));

        // qtext :=  "\" x | "\" x qtext | q | q qtext :=  ("\" x | q)+?
        PObject qtext =
            new PMultiple(new POr(new PSequence(new PChar('\\'), x), q));

        // string := char | char string := char+
        PObject string = new PMultiple(character);

        // quoted-string :=  """ qtext """
        PObject quoted_string =
            new PSequence(new PChar('"'), qtext, new PChar('"'));

        // dot-string := string | string "." dot-string := string ('.' string)*
        PObject dot_string =
            new PSequence(
                string,
                new PKleene(new PSequence(new PChar('\\'), string)));

        // local-part := dot-string | quoted-string
        PObject local_part = new POr(dot_string, quoted_string);

        // domain := {Domain}
        PObject domain = new example.internet.Domain();

        // mailbox := local-part "@" domain
        PObject mailbox = new PSequence(local_part, new PChar('@'), domain);

        parser = mailbox;
    }
    /**
     * Starts the application.
     * @param args an array of command-line arguments
     */
    public static void main(java.lang.String[] args) {
        Mailpath e = new Mailpath();

        System.out.println(e.process("<martijn@vanderlee.com>"));
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
