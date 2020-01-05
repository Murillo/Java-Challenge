package com.revolut.bank.currentaccount.service;

import com.revolut.bank.currentaccount.business.CurrentAccount;
import com.revolut.bank.currentaccount.business.Customer;
import com.revolut.bank.currentaccount.dao.CurrentAccountDao;
import com.revolut.bank.currentaccount.dao.CustomerCurrentAccountDao;
import com.revolut.bank.currentaccount.form.CreateCurrentAccountForm;
import com.revolut.bank.currentaccount.form.TransferBetweenAccountForm;
import com.revolut.bank.currentaccount.service.implementation.CurrentAccountServiceImpl;
import com.revolut.bank.exception.BusinessRuleException;
import com.revolut.bank.exception.RecordNotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import static java.util.Optional.empty;
import static java.util.Optional.of;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CurrentAccountServiceImplTest {

    private static final Long CUSTOMER_CODE = 1L;
    private static final Long ACCOUNT_CODE = 1L;
    private static final Double DEFAULT_AMOUNT = 100D;
    private CurrentAccountServiceImpl currentAccountService;

    @Mock
    private CurrentAccountDao currentAccountDao;

    @Mock
    private CustomerCurrentAccountDao customerCurrentAccountDao;

    @Before
    public void init(){
        MockitoAnnotations.initMocks(this);
        currentAccountService = new CurrentAccountServiceImpl(currentAccountDao, customerCurrentAccountDao);
    }

    @Test(expected = RecordNotFoundException.class)
    public void shouldNotCreateAnAccount_WhenUserIsNotFound(){
        when(customerCurrentAccountDao.getById(CUSTOMER_CODE)).thenReturn(empty());
        CreateCurrentAccountForm createCurrentAccountForm = new CreateCurrentAccountForm();
        createCurrentAccountForm.setCustomerCode(CUSTOMER_CODE);
        currentAccountService.create(createCurrentAccountForm);
    }

    @Test(expected = RecordNotFoundException.class)
    public void shouldNotWithdraw_WhenAccountIsNotFound(){
        when(currentAccountDao.getById(ACCOUNT_CODE)).thenReturn(empty());
        currentAccountService.withdraw(ACCOUNT_CODE, DEFAULT_AMOUNT);
    }

    @Test(expected = BusinessRuleException.class)
    public void shouldNotWithdraw_WhenAccountDoesNotHaveEnoughMoney(){
        CurrentAccount currentAccount = new CurrentAccount();
        currentAccount.setAmount(DEFAULT_AMOUNT);
        currentAccount.setActive(true);
        currentAccount.setCustomer(new Customer(CUSTOMER_CODE));
        when(currentAccountDao.getById(ACCOUNT_CODE)).thenReturn(of(currentAccount));
        currentAccountService.withdraw(ACCOUNT_CODE, DEFAULT_AMOUNT + 50);
    }

    @Test(expected = BusinessRuleException.class)
    public void shouldNotWithdraw_WhenAccountIsNotActivated(){
        CurrentAccount currentAccount = new CurrentAccount();
        currentAccount.setActive(false);
        currentAccount.setAmount(DEFAULT_AMOUNT);
        currentAccount.setCustomer(new Customer(CUSTOMER_CODE));
        when(currentAccountDao.getById(ACCOUNT_CODE)).thenReturn(of(currentAccount));
        currentAccountService.withdraw(ACCOUNT_CODE, DEFAULT_AMOUNT  - 50);
    }

    @Test(expected = BusinessRuleException.class)
    public void shouldNotWithdraw_WhenCustomerIsNull(){
        CurrentAccount currentAccount = new CurrentAccount();
        currentAccount.setCustomer(null);
        currentAccount.setActive(true);
        currentAccount.setAmount(DEFAULT_AMOUNT);
        when(currentAccountDao.getById(ACCOUNT_CODE)).thenReturn(of(currentAccount));
        currentAccountService.withdraw(ACCOUNT_CODE, DEFAULT_AMOUNT - 50);
    }

    @Test(expected = RecordNotFoundException.class)
    public void shouldNotDeposit_WhenAccountIsNotFound(){
        when(currentAccountDao.getById(ACCOUNT_CODE)).thenReturn(empty());
        currentAccountService.deposit(ACCOUNT_CODE, DEFAULT_AMOUNT);
    }

    @Test(expected = RecordNotFoundException.class)
    public void shouldNotShowBalance_WhenAccountIsNotFound(){
        when(currentAccountDao.getById(ACCOUNT_CODE)).thenReturn(empty());
        currentAccountService.balance(ACCOUNT_CODE);
    }

    @Test(expected = RecordNotFoundException.class)
    public void shouldNotTransfer_WhenFromAccountIsNotFound(){
        long FROM_ACCOUNT = ACCOUNT_CODE;
        long TO_ACCOUNT = ACCOUNT_CODE + 1;
        when(currentAccountDao.getById(FROM_ACCOUNT)).thenReturn(empty());
        TransferBetweenAccountForm transferBetweenAccountForm = new TransferBetweenAccountForm();
        transferBetweenAccountForm.setToAccountCode(TO_ACCOUNT);
        transferBetweenAccountForm.setValue(DEFAULT_AMOUNT);
        currentAccountService.transfer(FROM_ACCOUNT, transferBetweenAccountForm);
    }

    @Test(expected = RecordNotFoundException.class)
    public void shouldNotTransfer_WhenToAccountIsNotFound(){
        long FROM_ACCOUNT = ACCOUNT_CODE;
        long TO_ACCOUNT = ACCOUNT_CODE + 1;

        CurrentAccount fromCurrentAccount = new CurrentAccount();
        fromCurrentAccount.setCode(FROM_ACCOUNT);
        fromCurrentAccount.setActive(true);
        fromCurrentAccount.setAmount(DEFAULT_AMOUNT);

        when(currentAccountDao.getById(FROM_ACCOUNT)).thenReturn(of(fromCurrentAccount));
        when(currentAccountDao.getById(TO_ACCOUNT)).thenReturn(empty());

        TransferBetweenAccountForm transferBetweenAccountForm = new TransferBetweenAccountForm();
        transferBetweenAccountForm.setToAccountCode(TO_ACCOUNT);
        transferBetweenAccountForm.setValue(DEFAULT_AMOUNT);

        currentAccountService.transfer(FROM_ACCOUNT, transferBetweenAccountForm);
    }

}
