package app.theblu.cashflow.cs.domain;

import app.theblu.cashflow.cs.batteries.common.JPattern;
import lombok.Data;

import java.util.List;

@Data
public class FirstRule {
    private List<BlacklistedKeywordSet> blackListedKeywords;
    private List<BlacklistedRuleSet> blacklistedRules;

    public enum IgnoreReason {
        UNKNOWN_HEADER, IGNORE_BY_HEADER, IGNORE_BY_MSG, BLACK_LISTED_KEYBOARD, BLACK_LISTED_RULE
    }

    public IgnoreReason shouldIgnore(Msg msg) {
        for (BlacklistedKeywordSet keywordSet : blackListedKeywords) {
            return keywordSet.shouldIgnore(msg);
        }

        for (BlacklistedRuleSet ruleSet : blacklistedRules) {
            return ruleSet.shouldIgnore(msg);
        }
        return null;
    }

    @Data
    public static class BlacklistedKeywordSet {
        private String tag;
        private List<String> keywords;

        public IgnoreReason shouldIgnore(Msg msg) {
            String body = msg.lowerCaseBody();
            for (String value : keywords) {
                if (body.contains(value)) {
                    return IgnoreReason.BLACK_LISTED_KEYBOARD;
                }
            }
            return null;
        }
    }

    @Data
    public static class BlacklistedRuleSet {
        private String tag;
        private String description;
        private Apply apply;
        private List<JPattern> patterns;

        public enum Apply {
            HEADER, MESSAGE
        }

        public IgnoreReason shouldIgnore(Msg msg) {
            String text = apply == Apply.HEADER ? msg.getHeader() : msg.getBody();
            for (JPattern pattern : patterns) {
                if (pattern.hasMatch(text)) {
                    return IgnoreReason.BLACK_LISTED_RULE;
                }
            }
            return null;
        }
    }
}
