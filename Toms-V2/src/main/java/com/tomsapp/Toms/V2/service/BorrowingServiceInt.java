package com.tomsapp.Toms.V2.service;

import com.tomsapp.Toms.V2.dto.BorrowingDto;
import com.tomsapp.Toms.V2.entity.Borrowing;

import java.util.List;

public interface BorrowingServiceInt {



    List<BorrowingDto> borrowingList();

    void save(Borrowing borrowing);

    void delete(int borowId);

    Borrowing getByID(int borowId);
}
