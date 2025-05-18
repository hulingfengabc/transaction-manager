package com.homework.transactionmanager.controller;

import com.homework.transactionmanager.exception.ResourceNotFoundException;
import com.homework.transactionmanager.entity.Transaction;
import com.homework.transactionmanager.service.TransactionService;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    @Autowired
    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    /**
     * get transaction data with page
     * @param page page
     * @param size size
     * @param model model
     * @return transaction data with page
     */
    @GetMapping
    @RateLimiter(name = "transactionRateLimiter")
    public String getAllTransactions(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            Model model) {
        Page<Transaction> transactionPage = transactionService.getAllTransactions(
                PageRequest.of(page, size));
        model.addAttribute("transactions", transactionPage);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", transactionPage.getTotalPages());
        model.addAttribute("totalItems", transactionPage.getTotalElements());
        return "transactions";
    }

    /**
     * show add transaction page
     * @param model model
     * @return add transaction page
     */
    @GetMapping("/add")
    public String showAddTransactionForm(Model model) {
        model.addAttribute("transaction", new Transaction());
        return "add-transaction";
    }

    /**
     * add transaction
     * @param result result
     * @param model model
     * @return add transaction
     */
    @PostMapping("/add")
    @RateLimiter(name = "transactionWriteRateLimiter")
    public String addTransaction(@Valid @ModelAttribute("transaction") Transaction transaction,
                                 BindingResult result,
                                 Model model) {
        if (result.hasErrors()) {
            return "add-transaction";
        }

        try {
            transactionService.createTransaction(transaction);
            model.addAttribute("successMessage", "Add transaction Success！");
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "add-transaction";
        }

        return "redirect:/transactions";
    }

    /**
     * show edit transaction
     * @param id id
     * @param model model
     * @return edit transaction page
     */
    @GetMapping("/edit/{id}")
    public String showEditTransactionForm(@PathVariable("id") Long id, Model model) {
        model.addAttribute("transaction", transactionService.getTransactionById(id));
        return "edit-transaction";
    }

    /**
     * edit transaction
     * @param id id
     * @param transactionDetails transactionDetails
     * @param result result
     * @param model model
     * @return edit transaction
     */
    @PostMapping("/edit/{id}")
    @RateLimiter(name = "transactionWriteRateLimiter")
    public String updateTransaction(@PathVariable("id") Long id,
                                    @Valid @ModelAttribute("transaction") Transaction transactionDetails,
                                    BindingResult result,
                                    Model model) {
        if (result.hasErrors()) {
            return "edit-transaction";
        }

        try {
            transactionService.updateTransaction(id, transactionDetails);
            model.addAttribute("successMessage", "交易更新成功！");
        } catch (IllegalArgumentException | ResourceNotFoundException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "edit-transaction";
        }

        return "redirect:/transactions";
    }

    /**
     * delete transaction
     * @param id id
     * @param model model
     * @return page
     */
    @PostMapping("/delete/{id}")
    @RateLimiter(name = "transactionWriteRateLimiter")
    public String deleteTransaction(@PathVariable("id") Long id, Model model) {
        try {
            transactionService.deleteTransaction(id);
            model.addAttribute("successMessage", "交易删除成功！");
        } catch (ResourceNotFoundException e) {
            model.addAttribute("errorMessage", e.getMessage());
        }

        return "redirect:/transactions";
    }
}