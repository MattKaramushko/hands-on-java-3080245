package bank;
import java.util.Scanner;

import javax.security.auth.login.LoginException;

import bank.exceptions.AmountException;
public class Menu {
  private Scanner scanner;

  public static void main(String[] args)
  {
    System.out.println("Welcome to Fool's Bank, where your money is our money.");
    Menu menu = new Menu();
    menu.scanner = new Scanner(System.in);
    Customer customer = menu.authenticateUser();
    if(customer != null)
    {
      Account account = DataSource.getAccount(customer.getAccountId());
      menu.showMenu(customer, account);
    }
    menu.scanner.close();
  }

  private Customer authenticateUser()
  {
    System.out.println("please enter your username");
    String username = scanner.next();

    System.out.println("Please enter your password");
    String password=scanner.next();

    Customer customer = null;
    //why do we need to set it to null first??
    try
    {
      customer = Authenticator.login(username, password);
    }
    catch(LoginException e)
    {
      System.out.println("There was an error:" + e.getMessage());
    }
    return customer;
  }
  private void showMenu(Customer customer, Account account)
  {
    int selection = 0;
    while(selection!=4 && customer.isAuthenticated())
    {
      System.out.println("--------------\nPlease select an option:\n1:Deposit\n2:Withdrawl\n3:Check Balance\n4:Exit\n--------------");

      selection = scanner.nextInt();
      double amount = 0;
      switch(selection)
      {
        case 1:
          System.out.println("How much would you like to deposit?");
          amount = scanner.nextDouble();
          try {
            {
              account.deposit(amount);
            }
          } catch (AmountException e) {
            System.out.print(e.getMessage() + "\nTry Again.");
          }

          break;
        case 2:
          System.out.println("How much would you like to withdraw?");
          amount = scanner.nextDouble();
          try{
            account.withdraw(amount);
          }
          catch(AmountException e){
            System.out.println(e.getMessage()+" \nTry that one more time. I dare you.");

          }
          break;
        case 3:
          System.out.println("Current balance: " + account.getBalance());
          break;
        case 4:
          Authenticator.logout(customer);
          System.out.println("shoo!");
          break;
        default:
          System.out.println("Use your head, fool!");
          break;
      }
    } 
  }
}
