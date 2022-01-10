package sh.sunil.cart.spring.web;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import sh.sunil.cart.model.dto.ShoppingItem;
import sh.sunil.cart.model.dto.User;
import sh.sunil.cart.model.dto.UserRole;
import sh.sunil.cart.model.service.ShoppingCart;
import sh.sunil.cart.model.service.ShoppingService;
import sh.sunil.cart.web.WebObjectFactory;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/")
public class CartController {

    final static Logger log = LogManager.getLogger(CatalogController.class);

    //private ShoppingService shoppingService = WebObjectFactory.getShoppingService();
    @Autowired
    ShoppingService shoppingService = null;

    // note that scope is session in configuration
    // so the shopping cart is unique for each web session
    @Autowired
    ShoppingCart shoppingCart = null;

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

    @RequestMapping(value = "/cart", method = {RequestMethod.GET, RequestMethod.POST})
    public String viewCart(@RequestParam(name = "action", required = false) String action,
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

        if ("removeItemFromCart".equals(action)) {
            message = "removed " + itemName + " from cart";
            shoppingCart.removeItemFromCart(itemUuid);
        }

        List<ShoppingItem> shoppingCartItems = shoppingCart.getShoppingCartItems();
        Double shoppingcartTotal = shoppingCart.getTotal();

        // populate model with values
        model.addAttribute("shoppingCartItems", shoppingCartItems);
        model.addAttribute("shoppingcartTotal", shoppingcartTotal);
        model.addAttribute("message", message);
        model.addAttribute("errorMessage", errorMessage);
        log.info(String.format("Payload(action=%s, itemName=%s, itemUUID=%s, shoppingcartTotal=%s, message=%s, errorMessage=%s)", action, itemName, itemUuid, shoppingcartTotal, message, errorMessage));
        return "cart";
    }

}
