package sh.sunil.cart.spring.web;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import sh.sunil.bank.client.impl.BankRestClientImpl;
import sh.sunil.bank.model.client.BankRestClient;
import sh.sunil.bank.model.dto.BankTransactionStatus;
import sh.sunil.bank.model.dto.CreditCard;
import sh.sunil.bank.model.dto.TransactionReplyMessage;
import sh.sunil.cart.dao.impl.PropertiesDao;
import sh.sunil.cart.dao.impl.PropertiesWebObjectFactory;
import sh.sunil.cart.dao.impl.ShoppingItemCatalogRepository;
import sh.sunil.cart.model.dto.ShoppingItem;
import sh.sunil.cart.model.dto.User;
import sh.sunil.cart.model.dto.UserRole;
import sh.sunil.cart.model.service.ShoppingCart;
import sh.sunil.cart.model.service.ShoppingService;
import sh.sunil.cart.web.WebObjectFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

@Controller
@RequestMapping("/")
public class MVCController {

    final static Logger log = LogManager.getLogger(MVCController.class);

    //private ShoppingService shoppingService = WebObjectFactory.getShoppingService();
    @Autowired
    ShoppingService shoppingService = null;

    // note that scope is session in configuration
    // so the shopping cart is unique for each web session
    @Autowired
    ShoppingCart shoppingCart = null;

    @Autowired
    ShoppingItemCatalogRepository shoppingItemCatalogRepository;

    private User getSessionUser(HttpSession session) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        if (sessionUser == null) {
            sessionUser = new User();
            sessionUser.setUsername("anonymous");
            sessionUser.setUserRole(UserRole.ANONYMOUS);
            session.setAttribute("sessionUser", sessionUser);
        }
        return sessionUser;
    }

    @RequestMapping(value = "/", method = {RequestMethod.GET, RequestMethod.POST})
    public String homeCart(@RequestParam(name = "action", required = false) String action,
                           @RequestParam(name = "itemName", required = false) String itemName,
                           @RequestParam(name = "itemUUID", required = false) String itemUuid,
                           Model model,
                           HttpSession session) {

        // get sessionUser from session
        User sessionUser = getSessionUser(session);
        model.addAttribute("sessionUser", sessionUser);

        // used to set tab selected
        model.addAttribute("selectedPage", "home");

        String message = "";
        String errorMessage = "";

        ShoppingCart shoppingCart = (ShoppingCart) session.getAttribute("shoppingCart");
        if (shoppingCart == null) synchronized (this) {
            if (shoppingCart == null) {
                shoppingCart = WebObjectFactory.getNewShoppingCart();
                session.setAttribute("shoppingCart", shoppingCart);
            }
        }
        if (action == null) {
            // do nothing but show page
        } else if ("addItemToCart".equals(action)) {
            ShoppingItem shoppingItem = shoppingService.getNewItemByName(itemName);
            if (shoppingItem == null) {
                errorMessage = "cannot add unknown " + itemName + " to cart";
            } else {
                ShoppingItem stockItem = shoppingItemCatalogRepository.findByName(shoppingItem.getName()).get(0);
                if (shoppingService.checkStock(shoppingCart, shoppingItem.getName()) == false) {
                    errorMessage = String.format("Can not add item %s, we only have %s and you're trying to order %s.", shoppingItem.getName(), stockItem.getQuantity(), stockItem.getQuantity() + 1);
                } else {
                    message = "added " + itemName + " to cart";
                    shoppingCart.addItemToCart(shoppingItem);
                }
            }
        } else if ("removeItemFromCart".equals(action)) {
            message = "removed " + itemName + " from cart";
            shoppingCart.removeItemFromCart(itemUuid);
        } else {
            message = "unknown action=" + action;
        }

        List<ShoppingItem> availableItems = shoppingService.getAvailableItems();

        List<ShoppingItem> shoppingCartItems = shoppingCart.getShoppingCartItems();

        Double shoppingcartTotal = shoppingCart.getTotal();

        // populate model with values
        model.addAttribute("availableItems", availableItems);
        model.addAttribute("shoppingCartItems", shoppingCartItems);
        model.addAttribute("shoppingcartTotal", shoppingcartTotal);
        model.addAttribute("message", message);
        model.addAttribute("errorMessage", errorMessage);
        log.info(String.format("Payload(action=%s, itemName=%s, itemUUID=%s, availableItems=%s, shoppingcartTotal=%s, message=%s, errorMessage=%s)", action, itemName, itemUuid, availableItems, shoppingcartTotal, message, errorMessage));
        return "home";
    }

    @RequestMapping(value = "/about", method = {RequestMethod.GET, RequestMethod.POST})
    public String aboutCart(Model model, HttpSession session) {

        // get sessionUser from session
        User sessionUser = getSessionUser(session);
        model.addAttribute("sessionUser", sessionUser);

        // used to set tab selected
        model.addAttribute("selectedPage", "about");
        return "about";
    }

    @RequestMapping(value = "/contact", method = {RequestMethod.GET, RequestMethod.POST})
    public String contactCart(Model model, HttpSession session,
                              @RequestParam(name = "action", required = false) String action,
                              @RequestParam(name = "firstname", required = false) String firstName,
                              @RequestParam(name = "lastname", required = false) String lastName,
                              @RequestParam(name = "subject", required = false) String subject,
                              @RequestParam(name = "message", required = false) String contents) {
        // setup variables
        String message = "";
        String errorMessage = "";
        if (action == null) {
        } else if ("email".equals(action)) {
            message = String.format("Email from %s %s with the subject of <b> %s </b> and contents of %s was sent!", firstName, lastName, subject, contents);
        } else {
            errorMessage = "Unable to send email!";
        }
        // get sessionUser from session
        User sessionUser = getSessionUser(session);
        model.addAttribute("sessionUser", sessionUser);

        // used to set tab selected
        model.addAttribute("selectedPage", "contact");
        model.addAttribute("message", message);
        model.addAttribute("errorMessage", errorMessage);
        log.info(String.format("Payload(action=%s, firstname=%s, lastname=%s, subject=%s, contents=%s, message=%s, errorMessage=%s)", action, firstName, lastName, subject, contents, message, errorMessage));
        return "contact";
    }


    @RequestMapping(value = "/checkout", method = RequestMethod.GET)
    public String viewCheckout(
            Model model,
            HttpSession session) {

        // get sessionUser from session
        User sessionUser = getSessionUser(session);
        model.addAttribute("sessionUser", sessionUser);


        // used to set tab selected
        model.addAttribute("selectedPage", "checkout");

        String message = "";
        String errorMessage = "";

        if (UserRole.ANONYMOUS.equals(sessionUser.getUserRole())) {
            errorMessage = "You must be logged in to checkout!";
            model.addAttribute("errorMessage", errorMessage);
            return "checkout";
        }


        ShoppingCart shoppingCart = (ShoppingCart) session.getAttribute("shoppingCart");
        if (shoppingCart == null) synchronized (this) {
            if (shoppingCart == null) {
                shoppingCart = WebObjectFactory.getNewShoppingCart();
                session.setAttribute("shoppingCart", shoppingCart);
            }
        }


        List<ShoppingItem> shoppingCartItems = shoppingCart.getShoppingCartItems();

        Double shoppingcartTotal = shoppingCart.getTotal();

        // populate model with values
        model.addAttribute("shoppingCartItems", shoppingCartItems);
        model.addAttribute("shoppingcartTotal", shoppingcartTotal);
        model.addAttribute("message", message);
        model.addAttribute("errorMessage", errorMessage);
        return "checkout";
    }


    @RequestMapping(value = "/checkout", method = RequestMethod.POST)
    public String completeCheckout(
            @RequestParam(name = "cc_number", required = false) String cardnumber,
            @RequestParam(name = "cc_holder_name", required = false) String cardname,
            @RequestParam(name = "cc_expiry", required = false) String cardenddate,
            @RequestParam(name = "cc_issueNum", required = false) String cardissuenumber,
            @RequestParam(name = "cc_cvv", required = false) String cardcvv,
            Model model,
            HttpSession session) {


        // setup bank
        final PropertiesDao propertiesDao = PropertiesWebObjectFactory.getPropertiesDao();
        final String BANK_URL = propertiesDao.getProperty("BANK_API_URL");
        final String ShopCardNum = propertiesDao.getProperty("SHOP_CARD_NUMBER");

        final BankRestClient client = new BankRestClientImpl(BANK_URL);
        // get sessionUser from session
        User sessionUser = getSessionUser(session);
        model.addAttribute("sessionUser", sessionUser);

        // used to set tab selected
        model.addAttribute("selectedPage", "checkout");

        String message = "";
        String errorMessage = "";


        //Pay & Create Order
        CreditCard card = new CreditCard();
        card.setCardnumber(cardnumber);
//        card.setEndDate(cardenddate);
//        card.setIssueNumber(cardissuenumber);
//        card.setName(cardname);
//        card.setCvv(cardcvv);

        CreditCard shopCard = new CreditCard();
        shopCard.setCardnumber(ShopCardNum);

        ShoppingCart shoppingCart = (ShoppingCart) session.getAttribute("shoppingCart");
        if (shoppingCart == null) synchronized (this) {
            if (shoppingCart == null) {
                shoppingCart = WebObjectFactory.getNewShoppingCart();
                session.setAttribute("shoppingCart", shoppingCart);
            }
        }


        List<ShoppingItem> shoppingCartItems = shoppingCart.getShoppingCartItems();

        Double shoppingcartTotal = shoppingCart.getTotal();

        TransactionReplyMessage transfer = client.transferMoney(card, shopCard, shoppingcartTotal);

        if (BankTransactionStatus.SUCCESS.equals(transfer.getStatus())) message = "Transaction was successful.";
        if (BankTransactionStatus.FAIL.equals(transfer.getStatus()))
            errorMessage = "Transaction failed: " + transfer.getMessage();


        if (BankTransactionStatus.SUCCESS.equals(transfer.getStatus())) {
            for (ShoppingItem item : shoppingCartItems) {
                //Fetch Item from DB first
                ShoppingItem shoppingItem = shoppingItemCatalogRepository.findByName(item.getName()).get(0);
                if (shoppingItem != null) {
                    //Reduce Quantity
                    shoppingItem.setQuantity(shoppingItem.getQuantity() - item.getQuantity());
                    shoppingItemCatalogRepository.save(shoppingItem);
                }
            }
            shoppingCart.clearCart();
            shoppingCartItems = shoppingCart.getShoppingCartItems();
            shoppingcartTotal = shoppingCart.getTotal();
        }
        // populate model with values
        model.addAttribute("shoppingCartItems", shoppingCartItems);
        model.addAttribute("shoppingcartTotal", shoppingcartTotal);
        model.addAttribute("message", message);
        model.addAttribute("errorMessage", errorMessage);
        return "checkout";
    }


    /*
     * Default exception handler, catches all exceptions, redirects to friendly
     * error page. Does not catch request mapping errors
     */
    @ExceptionHandler(Exception.class)
    public String myExceptionHandler(final Exception e, Model model, HttpServletRequest request) {
        final StringWriter sw = new StringWriter();
        final PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        final String strStackTrace = sw.toString(); // stack trace as a string
        String urlStr = "not defined";
        if (request != null) {
            StringBuffer url = request.getRequestURL();
            urlStr = url.toString();
        }
        model.addAttribute("requestUrl", urlStr);
        model.addAttribute("strStackTrace", strStackTrace);
        model.addAttribute("exception", e);
        log.error(strStackTrace); // send to logger first
        return "error"; // default friendly exception message for sessionUser
    }

}
