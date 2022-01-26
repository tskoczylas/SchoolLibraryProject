package com.tomsapp.Toms.V2.repository;

import com.tomsapp.Toms.V2.entity.Borrow;
import com.tomsapp.Toms.V2.enums.BorrowPeriodEnum;
import com.tomsapp.Toms.V2.enums.BorrowStatusEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.persistence.TypedQuery;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface BorrowRepository extends JpaRepository<Borrow,Integer> {

    @Query("select u.borrowStatusEnum from Borrow u where u.student.id=:studentId" )
    Optional<List<BorrowStatusEnum>> findBorrowPeriodByStudentId(int studentId);

    @Query("select count(u.borrowStatusEnum) from Borrow u where u.student.id like :studentId and u.borrowStatusEnum not in :exemptBorrowStatus" )
    Optional<String> countActiveOrders(int studentId,List< BorrowStatusEnum> exemptBorrowStatus);

    Optional<List<Borrow>>findBorrowsByBorrowStatusEnum(BorrowStatusEnum borrowStatusEnum);

    @Query("select u from Borrow u where u.borrowStatusEnum like :enum and u.shipmentDate < :date" )
    Optional<List<Borrow>> findBorrowForSetBorrowed(@Param("enum") BorrowStatusEnum borrowStatusEnum, @Param("date") LocalDateTime date);

    @Query("select u from Borrow u where u.borrowStatusEnum like :enum and u.endBorrowDate < :date" )
    Optional<List<Borrow>> findBorrowForOverDue(@Param("enum") BorrowStatusEnum borrowStatusEnum, @Param("date") LocalDateTime date);


    @Query("select u from Borrow  u where u.borrowStatusEnum in :status and u.student.id = :student")
    Optional<List<Borrow>> findBorrowByStatusAndStudent
    (@Param("status") Collection<BorrowStatusEnum> borrowStatusEnum, @Param("student") int student);

    Optional<Borrow> findBorrowsByPayPalPaymentId(String paypalPaymentId);
}

