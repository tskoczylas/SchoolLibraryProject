package com.tomsapp.Toms.V2.service;

import com.tomsapp.Toms.V2.entity.Borrow;

public interface BorrowService {


    boolean logInStudentHasRightToOrder();

    Borrow   saveBorrow();
}
