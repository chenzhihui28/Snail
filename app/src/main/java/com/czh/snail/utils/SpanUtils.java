package com.czh.snail.utils;

import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.CharacterStyle;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author csl 2015年5月11日 下午3:33:03
 */
public final class SpanUtils {
    private SpanUtils() {
    }

    /**
     * 构造特定格式的字符串
     *
     * @param source
     * @param pattern
     * @param styles
     * @return
     *
     */
    public static CharSequence createSpannable(CharSequence source,
                                               Pattern pattern, CharacterStyle... styles) {
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(
                source);
        Matcher matcher = pattern.matcher(source);
        while (matcher.find()) {
            int start = matcher.start();
            int end = matcher.end();
            applyStylesToSpannable(spannableStringBuilder, start, end, styles);
        }
        return spannableStringBuilder;
    }

    public static SpannableStringBuilder applyStylesToSpannable(
            SpannableStringBuilder source, int start, int end,
            CharacterStyle... styles) {
        for (CharacterStyle style : styles) {
            source.setSpan(CharacterStyle.wrap(style), start, end,
                    Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        }
        return source;
    }
}
