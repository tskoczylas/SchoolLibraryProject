package com.tomsapp.Toms.V2.repository;

import com.tomsapp.Toms.V2.entity.Borrow;
import com.tomsapp.Toms.V2.enums.BorrowPeriodEnum;
import com.tomsapp.Toms.V2.enums.BorrowStatusEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface BorrowRepository extends JpaRepository<Borrow,Integer> {

    @Query("select u.borrowStatusEnum from Borrow u where u.student.id=:studentId" )
    Optional<List<BorrowStatusEnum>> findBorrowPeriodByStudentId(int studentId);

    @Query("select count(u.borrowStatusEnum) from Borrow u where u.student.id like :studentId and u.borrowStatusEnum not like :exemptBorrowStatus" )
    Optional<String> countActiveOrders(int studentId, BorrowStatusEnum exemptBorrowStatus);

}
