package edu.tongji.slug;

import java.text.Normalizer;
import java.util.Locale;
import java.util.regex.Pattern;

/**
 * Created by Breezewish on 6/13/15.
 */
public class UrlSlugGenerator {

    private static final Pattern NONLATIN = Pattern.compile("[^\\w-]");
    private static final Pattern WHITESPACE = Pattern.compile("[\\s]");

    public String toSlug(String input) {
        String nowhitespace = WHITESPACE.matcher(input).replaceAll("-");
        String normalized = Normalizer.normalize(nowhitespace, Normalizer.Form.NFD);
        String slug = NONLATIN.matcher(normalized).replaceAll("");
        return slug.toLowerCase(Locale.ENGLISH);
    }

}
