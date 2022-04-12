package edu.aleksandrTreskov.mms.service;

import edu.aleksandrTreskov.mms.dto.Cart;
import edu.aleksandrTreskov.mms.entity.DiscountCode;
import edu.aleksandrTreskov.mms.exception.DiscountCodeException;
import edu.aleksandrTreskov.mms.repository.DiscountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor

public class DiscountService {
    private final CartService cartService;
    private final DiscountRepository discountRepository;
    private final PurchaseService purchaseService;

    /**
     * Checks code on existing by its name
     *
     * @param code code's name
     * @return true or false
     */
    public boolean checkExistingCode(String code) {
        Optional<DiscountCode> optionalDiscountCode = discountRepository.findByName(code);
        return optionalDiscountCode.isPresent();
    }

    /**
     * Another check for existing, but returns percent discount and throws exception if data is incorrect
     *
     * @param cart Cart
     * @param code Code's name
     * @param email client's email
     * @return percent discount
     */
    public int findCode(Cart cart, String code, String email) {
        Optional<DiscountCode> optionalDiscountCode;
        if (code.startsWith("\"")) {
            optionalDiscountCode = discountRepository.findByName(code.substring(1, code.length() - 1));
        } else optionalDiscountCode = discountRepository.findByName(code);
        if (!checkExistingCode(code.substring(1,code.length()-1)))
            throw new NoSuchElementException("Discount code is not exists.");
        if (optionalDiscountCode.get().isDeleted())
            throw new DiscountCodeException("Discount code is expired.");
        if (optionalDiscountCode.get().getRequiredSumForActivation() > cartService.countTotalPriceInCart(cart))
            throw new DiscountCodeException("Conditions for activating the discount code are not met");
        if (purchaseService.getAllPurchasesForClient(email).isEmpty() && code.equals("WELCOME10"))
            throw new DiscountCodeException("This discount is available only for new users");
        return optionalDiscountCode.get().getPercentDiscount();
    }


    public void save(DiscountCode discountCode) {
        discountCode.setDeleted(false);
        discountRepository.save(discountCode);
    }

    public void delete(long id) {
        DiscountCode discountCode = discountRepository.findById(id).get();
        discountCode.setDeleted(true);
        discountRepository.save(discountCode);
    }

    public List<DiscountCode> findAll() {
        return discountRepository.findAll();
    }

    public DiscountCode findByName(String code) {
        return discountRepository.findByName(code).get();
    }
}
