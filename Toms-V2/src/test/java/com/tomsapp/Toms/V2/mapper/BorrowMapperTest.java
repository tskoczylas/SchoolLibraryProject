package com.tomsapp.Toms.V2.mapper;

import com.tomsapp.Toms.V2.dto.BorrowDto;
import com.tomsapp.Toms.V2.entity.Borrow;
import com.tomsapp.Toms.V2.entity.Student;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

import static org.assertj.core.internal.bytebuddy.matcher.ElementMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;

class BorrowMapperTest {


    @Test
    void mapToBorrowDtoFromBorrow() {
        //given
        Borrow borrow  = new Borrow();
        borrow.setId(4);
        borrow.setBooks(anyList());
        borrow.setStudent(any(Student.class));
        //when
        BorrowDto borrowDto = BorrowMapper.mapToBorrowDtoFromBorrow(borrow);
        //then
        assertEquals(borrowDto.getId(),4);
        assertEquals(borrowDto.getBooks(),anyList());
        assertEquals(borrowDto.getStudent(),any(Student.class));
    }
}