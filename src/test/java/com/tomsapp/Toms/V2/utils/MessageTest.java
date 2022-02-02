package com.tomsapp.Toms.V2.utils;

import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;


class MessageTest {

    @Test
    void createMessageMapShouldReturnStringMapWhenPropelInputProvide() {
        //given
        String input = "1;2;3;4";
        //when
        Map<String, String> messageMap = Message.createMessageMap(input);
        //then
        assertEquals(messageMap.get("messageTitle"), "1");
        assertEquals(messageMap.get("messageText"), "2");
        assertEquals(messageMap.get("link"), "3");
        assertEquals(messageMap.get("linkMes"), "4");
    }


    @Test
    void createMessageMapShouldReturnEmptyMapWhenPropelInputNotProvide() {
        //given
        String input = "1,2,3";
        //when
        Map<String, String> messageMap = Message.createMessageMap(input);
        //then
        assertTrue(messageMap.isEmpty());

    }

}