package sh.sunil.cart.spring.web;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import sh.sunil.cart.dao.impl.ShoppingItemCatalogRepository;
import sh.sunil.cart.model.dto.ShoppingItem;
import sh.sunil.cart.model.dto.User;
import sh.sunil.cart.model.dto.UserRole;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/")
public class CatalogController {

    final static Logger log = LogManager.getLogger(CatalogController.class);

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

    @RequestMapping(value = "/catalog", method = {RequestMethod.GET, RequestMethod.POST})
    public String catalogList(Model model, HttpSession session) {

        // get sessionUser from session
        User sessionUser = getSessionUser(session);
        model.addAttribute("sessionUser", sessionUser);

        if (!UserRole.ADMINISTRATOR.equals(sessionUser.getUserRole())) {
            model.addAttribute("errorMessage", "you must be an administrator to access this page.");
            return "catalog";
        }

        List<ShoppingItem> availableItems = shoppingItemCatalogRepository.findAll();

        model.addAttribute("availableItems", availableItems);

        // used to set tab selected
        model.addAttribute("selectedPage", "admin");
        return "catalog";
    }

    @RequestMapping(value = {"/viewModifyItem"}, method = RequestMethod.GET)
    public String viewModifyItem(
            @RequestParam(value = "itemName", required = true) String itemName,
            @RequestParam(value = "action", required = false) String action,
            Model model,
            HttpSession session) {
        String message = "";
        String errorMessage = "";

        // set values
        model.addAttribute("selectedPage", "admin");
        User sessionUser = getSessionUser(session);
        model.addAttribute("sessionUser", sessionUser);

        if (!UserRole.ADMINISTRATOR.equals(sessionUser.getUserRole())) {
            model.addAttribute("errorMessage", "you must be an administrator to access this page.");
            return "viewModifyItem";
        }

        List<ShoppingItem> shoppingItem = shoppingItemCatalogRepository.findByName(itemName);
        if (shoppingItem.isEmpty()) {
            errorMessage = "No items to find!";
            model.addAttribute("errorMessage", errorMessage);
            return "viewModifyItem";
        }

        ShoppingItem modifyItem = shoppingItem.get(0);
        model.addAttribute("modifyItem", modifyItem);

        model.addAttribute("message", message);
        model.addAttribute("errorMessage", errorMessage);
        if ("view".equals(action)) return "viewItem";
        if ("edit".equals(action)) return "viewModifyItem";
        if ("delete".equals(action)) {
            List<ShoppingItem> item = shoppingItemCatalogRepository.findByName(itemName);
            shoppingItemCatalogRepository.delete(item.get(0));
            List<ShoppingItem> availableItems = shoppingItemCatalogRepository.findAll();
            model.addAttribute("availableItems", availableItems);
            return "catalog";
        }
        return "viewModifyItem";
    }

    @RequestMapping(value = {"/viewModifyItem"}, method = RequestMethod.POST)
    public String updateItem(
            @RequestParam(value = "action", required = false) String action,
            @RequestParam(value = "id", required = false) Long id,
            @RequestParam(value = "name", required = true) String name,
            @RequestParam(value = "price", required = false) Double price,
            @RequestParam(value = "quantity", required = false) Integer quantity,
            Model model,
            HttpSession session) {
        String message = "";
        String errorMessage = "";


        // set values
        model.addAttribute("selectedPage", "admin");
        User sessionUser = getSessionUser(session);
        model.addAttribute("sessionUser", sessionUser);

        if (!UserRole.ADMINISTRATOR.equals(sessionUser.getUserRole())) {
            model.addAttribute("errorMessage", "you must be an administrator to access this page.");
            return "viewModifyItem";
        }


        Optional<ShoppingItem> shoppingItem = null;
        if (id != null) {
            shoppingItem = shoppingItemCatalogRepository.findById(id);
        }

        ShoppingItem modifyItem;
        if (shoppingItem == null || shoppingItem.isEmpty()) {
            modifyItem = new ShoppingItem();
        } else {
            modifyItem = shoppingItem.get();
        }


        if (quantity != null) modifyItem.setQuantity(quantity);
        if (name != null) modifyItem.setName(name);
        if (price != null) modifyItem.setPrice(price);
        shoppingItemCatalogRepository.save(modifyItem);
        message = "Updated item " + modifyItem.getName();
        model.addAttribute("message", message);
        model.addAttribute("errorMessage", errorMessage);
        model.addAttribute("modifyItem", modifyItem);
        return "viewItem";
    }

    @RequestMapping(value = {"/viewCreateItem"}, method = RequestMethod.GET)
    public String viewCreateItem(
            @RequestParam(value = "action", required = false) String action,
            Model model,
            HttpSession session) {
        String message = "";
        String errorMessage = "";

        // set values
        model.addAttribute("selectedPage", "admin");
        User sessionUser = getSessionUser(session);
        model.addAttribute("sessionUser", sessionUser);

        if (!UserRole.ADMINISTRATOR.equals(sessionUser.getUserRole())) {
            model.addAttribute("errorMessage", "you must be an administrator to access this page.");
            return "viewCreateItem";
        }

        return "viewCreateItem";
    }

    @RequestMapping(value = {"/viewCreateItem"}, method = RequestMethod.POST)
    public String createItem(
            @RequestParam(value = "action", required = false) String action,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "price", required = false) Double price,
            @RequestParam(value = "quantity", required = false) Integer quantity,
            Model model,
            HttpSession session) {
        String message = "";
        String errorMessage = "";

        // set values
        model.addAttribute("selectedPage", "admin");
        User sessionUser = getSessionUser(session);
        model.addAttribute("sessionUser", sessionUser);

        if (!UserRole.ADMINISTRATOR.equals(sessionUser.getUserRole())) {
            model.addAttribute("errorMessage", "you must be an administrator to access this page.");
            return "viewCreateItem";
        }
        if (name == null || price == null || quantity == null) {
            model.addAttribute("errorMessage", "Please fill in all the fields");
            return "viewCreateItem";
        }
        ShoppingItem shoppingItem = new ShoppingItem();
        shoppingItem.setName(name);
        shoppingItem.setQuantity(quantity);
        shoppingItem.setPrice(price);
        shoppingItemCatalogRepository.save(shoppingItem);
        message = "Added item " + shoppingItem.getName();
        model.addAttribute("errorMessage", errorMessage);
        model.addAttribute("message", message);
        return "viewCreateItem";
    }
}
