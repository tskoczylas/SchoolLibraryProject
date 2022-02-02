package com.tomsapp.Toms.V2.controller;

import com.tomsapp.Toms.V2.dto.BorrowDto;
import com.tomsapp.Toms.V2.enums.BorrowStatusEnum;
import com.tomsapp.Toms.V2.service.BorrowService;
import com.tomsapp.Toms.V2.session.BasketSession;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Collections;
import java.util.List;


@Controller
public class CheakoutService {

    BorrowService borrowService;
    BasketSession basketSession;

    public CheakoutService(BorrowService borrowService, BasketSession basketSession) {
        this.borrowService = borrowService;
        this.basketSession = basketSession;
    }

    @GetMapping("/cart")
    public String cartAccount(Model model) {
        List<BorrowDto> studentLogInBorrowsDto = borrowService.findStudentLogInBorrowsDto();
        model.addAttribute("borrow",studentLogInBorrowsDto);
        model.addAttribute("newOrders",borrowService.
                findByBorrStatusListLogIn(Collections.singletonList(BorrowStatusEnum.NEW)));
        model.addAttribute("paidOrders",borrowService.
                findByBorrStatusListLogIn(Collections.singletonList(BorrowStatusEnum.PAID)));
        model.addAttribute("overDueBorrow",borrowService.
                findByBorrStatusListLogIn(Collections.singletonList(BorrowStatusEnum.OVERDUE)));
        model.addAttribute("completeBorrow",borrowService.
                findByBorrStatusListLogIn(Collections.singletonList(BorrowStatusEnum.COMPLETE)));
        model.addAttribute("inHeldBorrow",borrowService.
                findByBorrStatusListLogIn(Collections.singletonList(BorrowStatusEnum.BORROWED)));

        model.addAttribute("basketSession",basketSession);
        return "cart";
    }

}
