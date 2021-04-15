/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import daos.CategoryDAO;
import daos.ProductDAO;
import dtos.CategoryDTO;
import dtos.ProductDTO;
import java.util.ArrayList;
import java.util.Vector;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import validation.ProductManagementValidation;

/**
 *
 * @author cutay
 */
public class ProductManagementTable extends javax.swing.JFrame {

    CategoryDAO categoryDAO = new CategoryDAO();
    ProductDAO productDAO = new ProductDAO();
    Vector<String> headerCategory = new Vector<>();
    Vector<String> headerProduct = new Vector<>();
    Vector dataCategory = new Vector();
    Vector dataProduct = new Vector();
    DefaultTableModel defaultModelCategory;
    DefaultTableModel defaultModelProduct;
    final int SUCCESS = 1;
    boolean addNewCategory = false;
    boolean addNewProduct = false;

    /**
     * Creates new form ProductManagementTable
     *
     * @throws java.lang.Exception
     */
    public ProductManagementTable() throws Exception {
        if (MainFrame.isLoggedIn) {
            initComponents();
            headerCategory.add("Category ID");
            headerCategory.add("Category Name");
            headerCategory.add("Description");

            defaultModelCategory = (DefaultTableModel) tbCategory.getModel();
            defaultModelCategory.setDataVector(dataCategory, headerCategory);

            headerProduct.add("Product ID");
            headerProduct.add("Product Name");
            headerProduct.add("Unit");
            headerProduct.add("Quantity");
            headerProduct.add("Price");
            headerProduct.add("Category ID");

            defaultModelProduct = (DefaultTableModel) tbProduct.getModel();
            defaultModelProduct.setDataVector(dataProduct, headerProduct);

            addCategoryName();
            loaddataCategory();
            loaddataProduct();

            notifyUserInCategory(false);
            notifyUserInProduct(false);

            System.out.println(btnProductSave.isVisible());

        } else {
            JOptionPane.showMessageDialog(this, "Please Login First");
            System.exit(0);
        }

    }

    // Tên User
    public void getFullName(String fullname) {
        lbGetFullName.setText("Welcome " + fullname);
    }

    // Ẩn các Thẻ
    public void notifyUserInCategory(boolean check) {
        lbCategoryID.setVisible(check);
        lbCategoryName.setVisible(check);
        lbDescription.setVisible(check);
        txtCategoryID.setVisible(check);
        txtCategoryName.setVisible(check);
        txtDescription.setVisible(check);
        btnCategorySave.setVisible(check);
        btnCategoryDelete.setVisible(check);
        lbTextCategory.setVisible(!check);
    }

    public void notifyUserInProduct(boolean check) {
        lbProductID.setVisible(check);
        lbProductName.setVisible(check);
        lbCbCategoryName.setVisible(check);
        lbUnit.setVisible(check);
        lbQuantity.setVisible(check);
        lbPrice.setVisible(check);
        txtProductID.setVisible(check);
        txtProductName.setVisible(check);
        cbCategoryName.setVisible(check);
        txtUnit.setVisible(check);
        txtQuantity.setVisible(check);
        txtPrice.setVisible(check);
        btnProductSave.setVisible(check);
        btnProductDelete.setVisible(check);
        lbTextProduct.setVisible(!check);
    }

    // Thêm nội dung từ Category qua Product
    public void addCategoryName() {
        try {
            ArrayList<String> list = categoryDAO.ChoiceCategory(categoryDAO.getAllCategory());
            for (String string : list) {
                cbCategoryName.addItem(string);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "load Category Name fall: ");
        }
    }

    public void checkCategoryName() {
        try {
            cbCategoryName.setSelectedIndex(0);
            cbCategoryName.setVisible(true);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "there isn't any Category Name");
        }
    }

    // Load Dữ Liệu lần đầu 
    public void loaddataCategory() {
        try {
            for (CategoryDTO string : categoryDAO.getAllCategory()) {
                Vector row = new Vector();
                row.add(string.getCategoryID());
                row.add(string.getCategoryName());
                row.add(string.getDescription());
                dataCategory.add(row);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "load Category fall: ");
        }
    }

    public void loaddataProduct() {
        try {
            for (ProductDTO string : productDAO.getAllProduct()) {
                Vector row = new Vector();
                row.add(string.getProductID());
                row.add(string.getProductName());
                row.add(string.getUnit());
                row.add(string.getQuantity());
                row.add(string.getPrice());
                row.add(string.getCategoryid());
                dataProduct.add(row);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "load Product fall: ");
        }
    }

    // Xóa Text ở button New, Save, Delete
    public void clearCategoryText() {
        txtCategoryID.setText("");
        txtCategoryName.setText("");
        txtDescription.setText("");
    }

    public void clearProductText() {
        txtProductID.setText("");
        txtProductName.setText("");
        txtUnit.setText("");
        txtQuantity.setText("");
        txtPrice.setText("");

        cbCategoryName.removeAllItems();
        addCategoryName();
        checkCategoryName();
    }

    // Bắt Validate ở Button Save
    public boolean catchCategoryValidate() {
        String categoryID = txtCategoryID.getText().trim();
        String categoryName = txtCategoryName.getText().trim();
        String description = txtDescription.getText().trim();
        ProductManagementValidation cateList = new ProductManagementValidation();

        if (addNewCategory == true) {
            if (!cateList.categoryIDValidation(categoryID)) {
                txtCategoryID.requestFocus();
                return false;
            }
        }

        if (!cateList.categoryNameValidation(categoryName)) {
            txtCategoryName.requestFocus();
            return false;
        }
        if (!cateList.descriptionValidation(description)) {
            txtDescription.requestFocus();
            return false;
        }
        return true;
    }

    public boolean catchProductValidate() {
        String productID = txtProductID.getText().trim();
        String productName = txtProductName.getText().trim();
        String unit = txtUnit.getText().trim();
        String quantity = txtQuantity.getText().trim();
        String price = txtPrice.getText().trim();
        ProductManagementValidation list = new ProductManagementValidation();

        if (addNewProduct == true) {
            if (!list.productIDValidation(productID)) {
                txtProductID.requestFocus();
                return false;
            }
        }

        if (!list.productNameValidation(productName)) {
            txtProductName.requestFocus();
            return false;
        }
        if (!list.unitValidation(unit)) {
            txtUnit.requestFocus();
            return false;
        }
        if (!list.quantityValidation(quantity)) {
            txtQuantity.requestFocus();
            return false;
        }
        if (!list.priceValidation(price)) {
            txtPrice.requestFocus();
            return false;
        }
        return true;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane3 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbCategory = new javax.swing.JTable();
        jPanel6 = new javax.swing.JPanel();
        lbCategoryID = new javax.swing.JLabel();
        lbCategoryName = new javax.swing.JLabel();
        lbDescription = new javax.swing.JLabel();
        btnCategoryAddNew = new javax.swing.JButton();
        btnCategorySave = new javax.swing.JButton();
        btnCategoryDelete = new javax.swing.JButton();
        txtCategoryID = new javax.swing.JTextField();
        txtCategoryName = new javax.swing.JTextField();
        txtDescription = new javax.swing.JTextField();
        lbTextCategory = new javax.swing.JLabel();
        lbGetFullName = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbProduct = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        lbProductID = new javax.swing.JLabel();
        lbProductName = new javax.swing.JLabel();
        lbCbCategoryName = new javax.swing.JLabel();
        lbUnit = new javax.swing.JLabel();
        lbQuantity = new javax.swing.JLabel();
        lbPrice = new javax.swing.JLabel();
        txtProductID = new javax.swing.JTextField();
        txtProductName = new javax.swing.JTextField();
        txtUnit = new javax.swing.JTextField();
        txtQuantity = new javax.swing.JTextField();
        txtPrice = new javax.swing.JTextField();
        cbCategoryName = new javax.swing.JComboBox<>();
        btnProductAddNew = new javax.swing.JButton();
        btnProductSave = new javax.swing.JButton();
        btnProductDelete = new javax.swing.JButton();
        lbTextProduct = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder("Main part:"));

        tbCategory.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbCategory.setDragEnabled(true);
        tbCategory.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbCategoryMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tbCategory);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(14, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2)
                .addContainerGap())
        );

        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder("Detailed part:"));

        lbCategoryID.setText("Category ID:");

        lbCategoryName.setText("Category Name:");

        lbDescription.setText("Description:");

        btnCategoryAddNew.setText("Add New");
        btnCategoryAddNew.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCategoryAddNewActionPerformed(evt);
            }
        });

        btnCategorySave.setText("Save");
        btnCategorySave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCategorySaveActionPerformed(evt);
            }
        });

        btnCategoryDelete.setText("Delete");
        btnCategoryDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCategoryDeleteActionPerformed(evt);
            }
        });

        lbTextCategory.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        lbTextCategory.setForeground(new java.awt.Color(255, 0, 51));
        lbTextCategory.setText("Click Add New Button to create new items");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(btnCategoryAddNew)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 115, Short.MAX_VALUE)
                .addComponent(btnCategorySave)
                .addGap(113, 113, 113)
                .addComponent(btnCategoryDelete)
                .addGap(32, 32, 32))
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(lbCategoryName)
                        .addGap(28, 28, 28)
                        .addComponent(txtCategoryName))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(lbDescription)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtDescription, javax.swing.GroupLayout.PREFERRED_SIZE, 368, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(lbTextCategory, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbCategoryID)
                .addGap(34, 34, 34)
                .addComponent(txtCategoryID))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(47, 47, 47)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbCategoryID)
                    .addComponent(txtCategoryID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(53, 53, 53)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbCategoryName)
                    .addComponent(txtCategoryName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(73, 73, 73)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbDescription)
                    .addComponent(txtDescription, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(43, 43, 43)
                .addComponent(lbTextCategory)
                .addGap(71, 71, 71)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCategoryAddNew)
                    .addComponent(btnCategorySave)
                    .addComponent(btnCategoryDelete))
                .addContainerGap(54, Short.MAX_VALUE))
        );

        lbGetFullName.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        lbGetFullName.setForeground(new java.awt.Color(255, 0, 51));
        lbGetFullName.setText("Full Name");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(31, 31, 31)
                        .addComponent(lbGetFullName, javax.swing.GroupLayout.PREFERRED_SIZE, 484, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(47, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(lbGetFullName)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        jTabbedPane3.addTab("Category", jPanel1);

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("Main part:"));

        tbProduct.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4", "Title 5", "Title 6"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbProduct.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbProductMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tbProduct);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 452, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 458, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Detailed part:"));

        lbProductID.setText("Product ID:");

        lbProductName.setText("Product Name:");

        lbCbCategoryName.setText("Category Name:");

        lbUnit.setText("Unit:");

        lbQuantity.setText("Quantity:");

        lbPrice.setText("Price:");

        btnProductAddNew.setText("Add New");
        btnProductAddNew.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnProductAddNewActionPerformed(evt);
            }
        });

        btnProductSave.setText("Save");
        btnProductSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnProductSaveActionPerformed(evt);
            }
        });

        btnProductDelete.setText("Delete");
        btnProductDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnProductDeleteActionPerformed(evt);
            }
        });

        lbTextProduct.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        lbTextProduct.setForeground(new java.awt.Color(255, 51, 51));
        lbTextProduct.setText("click Add New Button to create new items");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(40, 40, 40)
                        .addComponent(btnProductAddNew)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 143, Short.MAX_VALUE)
                        .addComponent(btnProductSave)
                        .addGap(118, 118, 118)
                        .addComponent(btnProductDelete)
                        .addGap(18, 18, 18))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lbCbCategoryName)
                            .addComponent(lbUnit)
                            .addComponent(lbProductName)
                            .addComponent(lbProductID)
                            .addComponent(lbQuantity)
                            .addComponent(lbPrice))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtPrice)
                            .addComponent(txtQuantity)
                            .addComponent(txtProductID)
                            .addComponent(txtProductName)
                            .addComponent(txtUnit)
                            .addComponent(cbCategoryName, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
            .addComponent(lbTextProduct, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbProductID)
                    .addComponent(txtProductID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbProductName)
                    .addComponent(txtProductName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbCbCategoryName)
                    .addComponent(cbCategoryName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbUnit)
                    .addComponent(txtUnit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(23, 23, 23)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbQuantity)
                    .addComponent(txtQuantity, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbPrice)
                    .addComponent(txtPrice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addComponent(lbTextProduct)
                .addGap(42, 42, 42)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnProductAddNew)
                    .addComponent(btnProductSave)
                    .addComponent(btnProductDelete))
                .addContainerGap(121, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        jTabbedPane3.addTab("Product", jPanel3);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane3)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane3)
                .addContainerGap())
        );

        jTabbedPane3.getAccessibleContext().setAccessibleName("Category");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    public boolean checkTextProduct() {
        if (!"".equals(txtProductName.getText().trim())) {
            return false;
        }
        if (!"".equals(txtProductID.getText().trim())) {
            return false;
        }
        if (!"".equals(txtUnit.getText().trim())) {
            return false;
        }
        if (!"".equals(txtPrice.getText().trim())) {
            return false;
        } else if (!"".equals(txtQuantity.getText().trim())) {
            return false;
        }
        return true;
    }

    public boolean checkTextCategory() {
        if (!"".equals(txtCategoryID.getText().trim())) {
            return false;
        }
        if (!"".equals(txtCategoryName.getText().trim())) {
            return false;
        } else if (!"".equals(txtDescription.getText().trim())) {
            return false;
        }
        return true;
    }

    public void ProductMouseClicked() {
        try {
            txtProductID.setEditable(false);
            int row = tbProduct.getSelectedRow();

            for (int i = 0; i < 6; i++) {
                if ("Product ID".equals(tbProduct.getColumnName(i))) {
                    txtProductID.setText(tbProduct.getValueAt(row, i).toString());
                }
                if ("Product Name".equals(tbProduct.getColumnName(i))) {
                    txtProductName.setText(tbProduct.getValueAt(row, i).toString());
                }
                if ("Unit".equals(tbProduct.getColumnName(i))) {
                    txtUnit.setText(tbProduct.getValueAt(row, i).toString());
                }
                if ("Quantity".equals(tbProduct.getColumnName(i))) {
                    txtQuantity.setText(tbProduct.getValueAt(row, i).toString());
                }
                if ("Price".equals(tbProduct.getColumnName(i))) {
                    txtPrice.setText(tbProduct.getValueAt(row, i).toString());
                } else if ("Category ID".equals(tbProduct.getColumnName(i))) {
                    String CategoryID = tbProduct.getValueAt(row, i).toString();
                    ArrayList<String> list = categoryDAO.ChoiceCategory(categoryDAO.getAllCategory());
                    for (String string : list) {
                        if (CategoryID.equalsIgnoreCase(string.substring(0, 5))) {
                            cbCategoryName.setSelectedItem(string);
                        }
                    }
                }

            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Clicked Product fall: ");
        }
    }

    public void CategoryMouseClicked() {
        try {
            txtCategoryID.setEditable(false);
            int row = tbCategory.getSelectedRow();

            for (int i = 0; i < 3; i++) {
                if ("Category ID".equals(tbCategory.getColumnName(i))) {
                    txtCategoryID.setText(tbCategory.getValueAt(row, i).toString());
                }
                if ("Category Name".equals(tbCategory.getColumnName(i))) {
                    txtCategoryName.setText(tbCategory.getValueAt(row, i).toString());
                } else if ("Description".equals(tbCategory.getColumnName(i))) {
                    txtDescription.setText(tbCategory.getValueAt(row, i).toString());
                }
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Clicked Category fall: ");
        }
    }

    private void tbProductMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbProductMouseClicked
        if (!btnProductSave.isVisible()) {
            notifyUserInProduct(true);
        }
        if (addNewProduct == true) {
            if (checkTextProduct() == false) {
                int YesOrNo = JOptionPane.showConfirmDialog(this, "do you want to lose it",
                        "Check", JOptionPane.YES_NO_OPTION);
                if (YesOrNo == JOptionPane.YES_OPTION) {
                    ProductMouseClicked();
                } else {
                    addNewProduct = true;
                    return;
                }
            } else {
                ProductMouseClicked();
            }
        } else {
            ProductMouseClicked();
        }
        addNewProduct = false;
    }//GEN-LAST:event_tbProductMouseClicked

    private void tbCategoryMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbCategoryMouseClicked
        if (!btnCategorySave.isVisible()) {
            notifyUserInCategory(true);
        }
        if (addNewCategory == true) {
            if (checkTextCategory() == false) {
                int YesOrNo = JOptionPane.showConfirmDialog(this, "do you want to lose it",
                        "Check", JOptionPane.YES_NO_OPTION);
                if (YesOrNo == JOptionPane.YES_OPTION) {
                    CategoryMouseClicked();
                } else {
                    addNewCategory = true;
                    return;
                }
            } else {
                CategoryMouseClicked();
            }
        } else {
            CategoryMouseClicked();
        }
        addNewCategory = false;
    }//GEN-LAST:event_tbCategoryMouseClicked

    private void btnCategoryAddNewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCategoryAddNewActionPerformed
        if (!btnCategorySave.isVisible()) {
            notifyUserInCategory(true);
        }
        txtCategoryID.setEditable(true);
        txtCategoryID.requestFocus();
        clearCategoryText();
        tbCategory.clearSelection();
        addNewCategory = true;
    }//GEN-LAST:event_btnCategoryAddNewActionPerformed

    private void btnProductAddNewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnProductAddNewActionPerformed
        if (!btnProductSave.isVisible()) {
            notifyUserInProduct(true);
        }
        txtProductID.setEditable(true);
        txtProductID.requestFocus();
        clearProductText();
        tbProduct.clearSelection();
        addNewProduct = true;
    }//GEN-LAST:event_btnProductAddNewActionPerformed

    private void btnProductSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnProductSaveActionPerformed
        if (addNewProduct == true) {
            if (catchProductValidate()) {
                String productID = txtProductID.getText().trim();
                String productName = txtProductName.getText().trim();
                int getIndex = cbCategoryName.getSelectedIndex();
                String categoryID;
                if (!cbCategoryName.isVisible()) {
                    clearProductText();
                    return;
                } else {
                    try {
                        categoryID = cbCategoryName.getItemAt(getIndex).sub(0, 5);
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(this, "fail Category Name");
                        return;
                    }
                }
                String unit = txtUnit.getText().trim();
                String number = txtQuantity.getText().trim();
                int Quantity = Integer.parseInt(number);
                String salary = txtPrice.getText().trim();
                float price = Float.parseFloat(salary);

                ProductDTO dto = new ProductDTO(productID, productName, unit, categoryID, Quantity, price);

                try {
                    if (productDAO.CheckSameID(productID) != null) {
                        txtProductID.requestFocus();
                        JOptionPane.showMessageDialog(this, "Code is duplicate");
                        return;
                    }
                    if (productDAO.insertProduct(dto) == SUCCESS) {
                        Vector row = new Vector();
                        row.add(productID);
                        row.add(productName);
                        row.add(unit);
                        row.add(Quantity);
                        row.add(price);
                        row.add(categoryID);
                        dataProduct.add(row);
                        tbProduct.clearSelection();
                        JOptionPane.showMessageDialog(this, "Save Success");
                        clearProductText();
                        tbProduct.updateUI();
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Product Save fall: ");
                }
            } else {
                return;
            }
        } else {
            if (catchProductValidate()) {
                String productID = txtProductID.getText().trim();
                String productName = txtProductName.getText().trim();
                int getIndex = cbCategoryName.getSelectedIndex();
                String categoryID;
                try {
                    categoryID = cbCategoryName.getItemAt(getIndex).substring(0, 5);
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(this, "fail Category Name");
                    return;
                }

                String unit = txtUnit.getText().trim();
                String number = txtQuantity.getText().trim();
                int Quantity = Integer.parseInt(number);
                String salary = txtPrice.getText().trim();
                float price = Float.parseFloat(salary);

                ProductDTO dto = new ProductDTO(productID, productName, unit, categoryID, Quantity, price);

                try {
                    if (productDAO.updateProduct(dto) == SUCCESS) {
                        int selectRow = tbProduct.getSelectedRow();
                        Vector row = (Vector) dataProduct.get(selectRow);
                        row.set(1, productName);
                        row.set(2, unit);
                        row.set(3, number);
                        row.set(4, salary);
                        row.set(5, categoryID);
                        tbCategory.clearSelection();
                        JOptionPane.showMessageDialog(this, "Update Success");
                        clearProductText();
                        tbProduct.updateUI();
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Product Update fall: ");
                }
            } else {
                return;
            }
        }
        addNewProduct = true;
    }//GEN-LAST:event_btnProductSaveActionPerformed

    private void btnCategorySaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCategorySaveActionPerformed
        if (addNewCategory == true) {
            if (catchCategoryValidate()) {
                String categoryID = txtCategoryID.getText().trim();
                String categoryName = txtCategoryName.getText().trim();
                String description = txtDescription.getText().trim();
                CategoryDTO dto = new CategoryDTO(categoryID, categoryName, description);

                try {
                    if (categoryDAO.CheckSameID(categoryID) != null) {
                        txtCategoryID.requestFocus();
                        JOptionPane.showMessageDialog(this, "Code is duplicate");
                        return;
                    }
                    if (categoryDAO.insertCategory(dto) == SUCCESS) {
                        Vector row = new Vector();
                        row.add(categoryID);
                        row.add(categoryName);
                        row.add(description);
                        dataCategory.add(row);
                        cbCategoryName.removeAllItems();
                        addCategoryName();
                        if (btnProductSave.isVisible()) {
                            notifyUserInProduct(true);
                        } else {
                            notifyUserInProduct(false);
                        }
                        JOptionPane.showMessageDialog(this, "Save Success");
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Category Save fall: ");
                }
            } else {
                return;
            }
        } else {
            if (catchCategoryValidate()) {
                String categoryID = txtCategoryID.getText().trim();
                String categoryName = txtCategoryName.getText().trim();
                String description = txtDescription.getText().trim();
                CategoryDTO dto = new CategoryDTO(categoryID, categoryName, description);

                try {
                    if (categoryDAO.updateCategory(dto) == SUCCESS) {
                        int selectedRow = tbCategory.getSelectedRow();
                        Vector row = (Vector) dataCategory.get(selectedRow);
                        row.set(1, categoryName);
                        row.set(2, description);
                        cbCategoryName.removeAllItems();
                        addCategoryName();
                        tbCategory.clearSelection();
                        JOptionPane.showMessageDialog(this, "Update Success");
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Category Update fall: " + ex.getMessage());
                }
            } else {
                return;
            }
        }
        clearCategoryText();
        tbCategory.updateUI();
    }//GEN-LAST:event_btnCategorySaveActionPerformed

    private void btnProductDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnProductDeleteActionPerformed
        String code = txtProductID.getText().trim();
        if (addNewProduct == true) {
            JOptionPane.showMessageDialog(this, "Choose the row!");
        } else {
            try {
                int YesOrNo = JOptionPane.showConfirmDialog(this, "do you want to delete it",
                        "Delete", JOptionPane.YES_NO_OPTION);
                if (YesOrNo == JOptionPane.YES_OPTION) {
                    if (productDAO.deleteProduct(code) == SUCCESS) {
                        dataProduct.remove(tbProduct.getSelectedRow());
                        tbProduct.updateUI();
                        clearProductText();
                        txtProductID.setEditable(true);
                        addNewProduct = true;
                        tbProduct.clearSelection();
                        JOptionPane.showMessageDialog(this, "Delete Success");
                    }
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Product delete fall: ");
            }
        }
    }//GEN-LAST:event_btnProductDeleteActionPerformed

    private void btnCategoryDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCategoryDeleteActionPerformed
        String code = txtCategoryID.getText().trim();
        if (addNewCategory) {
            JOptionPane.showMessageDialog(this, "please choose the row");
        } else {
            try {
                if (categoryDAO.checkSameIDWithProduct(code) != null) {
                    JOptionPane.showMessageDialog(this, "Sorry There're items that relative to Category, please delete them first");
                } else {
                    int YesOrNo = JOptionPane.showConfirmDialog(this, "do you want to delete it",
                            "Delete", JOptionPane.YES_NO_OPTION);
                    if (YesOrNo == JOptionPane.YES_OPTION) {
                        if (categoryDAO.deleteCategory(code) == SUCCESS) {
                            dataCategory.remove(tbCategory.getSelectedRow());

                            cbCategoryName.removeAllItems();
                            addCategoryName();
                            checkCategoryName();
                            tbCategory.updateUI();
                            tbProduct.updateUI();
                            clearCategoryText();
                            txtCategoryID.setEditable(true);
                            addNewCategory = true;
                            tbCategory.clearSelection();
                            JOptionPane.showMessageDialog(this, "Delete Success");
                        }
                    }
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Category Delete fall: ");
            }
        }

    }//GEN-LAST:event_btnCategoryDeleteActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ProductManagementTable.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ProductManagementTable.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ProductManagementTable.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ProductManagementTable.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new ProductManagementTable().setVisible(true);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCategoryAddNew;
    private javax.swing.JButton btnCategoryDelete;
    private javax.swing.JButton btnCategorySave;
    private javax.swing.JButton btnProductAddNew;
    private javax.swing.JButton btnProductDelete;
    private javax.swing.JButton btnProductSave;
    private javax.swing.JComboBox<String> cbCategoryName;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane jTabbedPane3;
    private javax.swing.JLabel lbCategoryID;
    private javax.swing.JLabel lbCategoryName;
    private javax.swing.JLabel lbCbCategoryName;
    private javax.swing.JLabel lbDescription;
    private javax.swing.JLabel lbGetFullName;
    private javax.swing.JLabel lbPrice;
    private javax.swing.JLabel lbProductID;
    private javax.swing.JLabel lbProductName;
    private javax.swing.JLabel lbQuantity;
    private javax.swing.JLabel lbTextCategory;
    private javax.swing.JLabel lbTextProduct;
    private javax.swing.JLabel lbUnit;
    private javax.swing.JTable tbCategory;
    private javax.swing.JTable tbProduct;
    private javax.swing.JTextField txtCategoryID;
    private javax.swing.JTextField txtCategoryName;
    private javax.swing.JTextField txtDescription;
    private javax.swing.JTextField txtPrice;
    private javax.swing.JTextField txtProductID;
    private javax.swing.JTextField txtProductName;
    private javax.swing.JTextField txtQuantity;
    private javax.swing.JTextField txtUnit;
    // End of variables declaration//GEN-END:variables
}
