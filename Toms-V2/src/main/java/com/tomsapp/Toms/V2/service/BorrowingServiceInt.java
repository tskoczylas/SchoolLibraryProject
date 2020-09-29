package com.tomsapp.Toms.V2.service;

import com.tomsapp.Toms.V2.entity.Books;
import com.tomsapp.Toms.V2.entity.Borrowing;
import com.tomsapp.Toms.V2.entity.Student;
import com.tomsapp.Toms.V2.repository.BorrowingRepository;
import com.tomsapp.Toms.V2.session.BasketSession;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class BorrowingServiceInt implements BorrowingService {


    BasketSession basketSession;
    BorrowingRepository borrowingRepository;
    StudentService studentService;

    public BorrowingServiceInt(BasketSession basketSession, BorrowingRepository borrowingRepository, StudentService studentService) {
        this.basketSession = basketSession;
        this.borrowingRepository = borrowingRepository;
        this.studentService = studentService;
    }
    @Override
    public void   saveBorrow(){
        List<Borrowing> borrowingList = new ArrayList<>();
        Iterator<Books> booksIterator = basketSession.getSelectBooks().iterator();
        Student logInStudent = studentService.findLogInStudent();
        while (booksIterator.hasNext()){
            Borrowing borrowing = new Borrowing();
            borrowing.setBooks(booksIterator.next());
            borrowing.setStudent(logInStudent);
            borrowing.setBorrowDaysEnum(basketSession.getBorrowDaysEnum());
            borrowingList.add(borrowing); }
        borrowingRepository.saveAll(borrowingList);
        basketSession.cleanBooksBasket();
        basketSession.resetBorrowDaysEnum();

    }


}
