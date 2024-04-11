package MainPuntoVenta;

import Controller.ControllerAddProduct;
import Controller.ControllerConsultation;
import Controller.ControllerConsultationHighUser;
import Controller.ControllerMenu;
import Controller.HighUserController;
import Controller.LoginController;
import Controller.ViewBuyController;
import Controller.ViewHighClientController;
import Controller.ViewSaleController;
import Model.CRUD_BD;
import Model.Clients;
import Model.ConnectionBD;
import Model.CrudBuy;
import Model.CrudCategory;
import Model.CrudClient;
import Model.CrudProductsSale;
import Model.CrudUserBD;

import Model.DateTime;
import Model.Products;
import Model.Users;
import View.ViewAddProduct;
import View.ViewBuy;
import View.ViewConsultation;
import View.ViewConsultationUser;
import View.ViewHighClient;
import View.ViewHighUser;
import View.ViewLogin;
import View.ViewMenu;
import View.ViewSale;

import javax.swing.table.DefaultTableModel;

public class PuntoVenta {

    public static void main(String[] args) {
        DateTime objDateTime = new DateTime();
        Users objUsers = new Users();
//        Category objCategory = new  Category();
        Products objProducts = new Products();
        Clients objClients = new Clients();
        ViewBuy objViewBuy = new ViewBuy();
        ViewSale objViewSale = new ViewSale();
        ViewMenu objViewMenu = new ViewMenu();
        ViewLogin objViewLogin = new ViewLogin();
        ViewHighUser objViewHighUser = new ViewHighUser();
        ViewHighClient objViewHighClient = new ViewHighClient();
        ViewAddProduct objViewAddProduct = new ViewAddProduct();
        ViewConsultation objViewConsultation = new ViewConsultation();
        ViewConsultationUser objViewConsultationUser = new ViewConsultationUser();

        // Crear una sola instancia de ConnectionBD (patrón Singleton)
        ConnectionBD objConnectionBD = ConnectionBD.obtenerInstancia();

        CRUD_BD objCrud = new CRUD_BD(objConnectionBD);
        CrudBuy objCrudBuy = new CrudBuy(objConnectionBD, objProducts);
        CrudCategory objCrudCategory = new CrudCategory(objConnectionBD);
        CrudClient objCrudClient = new CrudClient(objConnectionBD, objClients);
        CrudUserBD objCrudUserBD = new CrudUserBD(objConnectionBD, objUsers);
        CrudProductsSale objCrudProductsSale = new CrudProductsSale(objConnectionBD, objUsers);

        // Aquí deberías definir tableModel
        DefaultTableModel tableModel = new DefaultTableModel();

        ViewHighClientController objViewHighClientController = new ViewHighClientController(objViewHighClient, objClients, objCrudClient, objConnectionBD);

        ViewBuyController objViewBuyController = new ViewBuyController(objViewBuy, tableModel, objCrudBuy, objConnectionBD, objUsers, objDateTime, objClients, objProducts);
        ViewSaleController objViewSaleController = new ViewSaleController(objViewSale, objConnectionBD, objCrudProductsSale, tableModel, objViewBuyController, objCrudCategory, objClients);

        LoginController objLoginController = new LoginController(objUsers, objViewLogin, objConnectionBD, objViewHighClientController, objViewSaleController);
        ControllerConsultation objControllerConsultation = new ControllerConsultation(objViewConsultation, objCrud, objConnectionBD);
        ControllerAddProduct objControllerAddProduct = new ControllerAddProduct(objViewAddProduct, objUsers, objConnectionBD, objCrud, objProducts);

        ControllerConsultationHighUser objControllerConsultationHighUser = new ControllerConsultationHighUser(objCrudUserBD, objViewConsultationUser, tableModel);
        HighUserController objControllerHighUser = new HighUserController(objViewHighUser, objCrudUserBD, objConnectionBD, objControllerConsultationHighUser);

        ControllerMenu objControllerMenu = new ControllerMenu(objViewMenu, objCrud,
                objControllerAddProduct, objControllerConsultation, objLoginController, objControllerHighUser, objConnectionBD, objUsers,
                objViewSaleController, objViewHighClientController);

        //Configura ControllerMenu para que tenga acceso a objLoginController
        objLoginController.setControllerMenu(objControllerMenu);
        // Configura ControllerMenu para que tenga acceso a ControllerAddProduct
        objControllerMenu.setControllerAddProduct(objControllerAddProduct);

        // Configura ControllerAddProduct para que tenga acceso a ControllerMenu
        objControllerAddProduct.setControllerMenu(objControllerMenu);

        // Configura ControllerConsultation para que tenga acceso a ControllerMenu
        objControllerConsultation.setControllerMenu(objControllerMenu);

        objControllerHighUser.setControllerMenu(objControllerMenu);

        objViewHighClientController.setLoginController(objLoginController);

        objViewSaleController.setObjLoginController(objLoginController);

        objViewSaleController.setControllerMenu(objControllerMenu);

        objControllerConsultationHighUser.setControllerMenu(objControllerHighUser);

        objViewBuyController.setObjViewSaleController(objViewSaleController);

        objLoginController.showLoginView();
        // objLoginController.setControllerMenu(objControllerMenu);
        objControllerMenu.showMenuView();
    }
}
