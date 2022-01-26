package com.tomsapp.Toms.V2.mapper;

import com.tomsapp.Toms.V2.dto.BorrowDto;
import com.tomsapp.Toms.V2.dto.StudentAddressDto;
import com.tomsapp.Toms.V2.entity.Borrow;
import com.tomsapp.Toms.V2.entity.Student;
import com.tomsapp.Toms.V2.entity.Token;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;

public class BorrowMapper {

    static ModelMapper modelMapper;


    public static BorrowDto mapToBorrowDtoFromBorrow(Borrow borrow) {
        modelMapper = new ModelMapper();
        BorrowDto borrowDto = new BorrowDto();
        modelMapper.map(borrow, borrowDto);
        return borrowDto;
    }


    public static Borrow mapToBorrowFromBorrowDto(BorrowDto borrowDto) {
        modelMapper = new ModelMapper();
        Borrow borrow = new Borrow();
        modelMapper.map(borrowDto, borrow);
        return borrow;
    }


}
