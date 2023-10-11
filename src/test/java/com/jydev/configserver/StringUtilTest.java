package com.jydev.configserver;


import com.jydev.configserver.util.StringUtil;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class StringUtilTest {

    @ParameterizedTest(name = "{index}: \"{0}\" 입력이 주어진 경우 aaa로 반환")
    @ValueSource(strings = {"a  aa", " aa a", "a a a    ", "a   aa  ", "aaa"})
    void 모든_공백_제거_테스트(String param) {
        Assertions.assertThat(StringUtil.removeAllWhiteSpace(param)).isEqualTo("aaa");
    }
}
