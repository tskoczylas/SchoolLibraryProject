package com.tomsapp.Toms.V2.service;

import com.tomsapp.Toms.V2.dto.BorrowDto;
import com.tomsapp.Toms.V2.entity.Borrow;
import com.tomsapp.Toms.V2.enums.BorrowStatusEnum;

import java.util.List;
import java.util.Optional;

public interface BorrowService {


    boolean logInStudentHasRightToOrder();

    List<BorrowDto> findStudentLogInBorrowsDto();


    List<BorrowDto> findByBorrStatusListLogIn(List<BorrowStatusEnum> borrowStatusEnum);





    Optional<BorrowDto> createBorrow();


    BorrowDto saveBorrowDto(BorrowDto borrowDto);

    void changeBorrowStatus(String borrowId, BorrowStatusEnum borrowStatusEnum);

    Optional<Borrow> findBorrowById(String borrowId);
}
