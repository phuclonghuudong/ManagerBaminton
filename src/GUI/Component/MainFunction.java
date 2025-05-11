package GUI.Component;

import java.awt.*;
import java.util.*;
import javax.swing.*;
import style.style;

/**
 *
 * @author phucp
 */
public class MainFunction extends JToolBar {

    public ButtonToolBar btnAdd, btnDelete, btnEdit, btnDetail, btnNhapExcel, btnXuatExcel, btnHuyPhieu;
    public JSeparator separator1;
    public HashMap<String, ButtonToolBar> btn = new HashMap<>();
    style style = new style();

    public MainFunction(String chucnang, String[] listBtn) {
        initData();
        initComponent(chucnang, listBtn);
    }

    public void initData() {
        btn.put("create", new ButtonToolBar("THÊM", "add.svg", "create"));
        btn.put("delete", new ButtonToolBar("XÓA", "delete.svg", "delete"));
        btn.put("update", new ButtonToolBar("SỬA", "edit.svg", "update"));
        btn.put("cancel", new ButtonToolBar("HUỶ PHIẾU", "cancel.svg", "delete"));
        btn.put("detail", new ButtonToolBar("CHI TIẾT", "detail.svg", "view"));
        btn.put("import", new ButtonToolBar("NHẬP EXCEL", "import_excel.svg", "create"));
        btn.put("export", new ButtonToolBar("XUẤT EXCEL", "export_excel.svg", "view"));
        btn.put("phone", new ButtonToolBar("XEM DS", "phone.svg", "view"));

//        style.buttonCustome(btnAdd);
//        style.buttonCustome(btnDelete);
//        style.buttonCustome(btnEdit);
//        style.buttonCustome(btnDetail);
//        style.buttonCustome(btnNhapExcel);
//        style.buttonCustome(btnXuatExcel);
//        style.buttonCustome(btnHuyPhieu);
    }

    private void initComponent(String chucnang, String[] listBtn) {
        this.setBackground(Color.WHITE);
        this.setFloatable(false);
        this.setBorder(BorderFactory.createEmptyBorder());
        this.setRollover(true);
//        initData();
        for (String btnn : listBtn) {
            this.add(btn.get(btnn));
//            btn.get(btnn).setEnabled(false);
        }
    }
}
