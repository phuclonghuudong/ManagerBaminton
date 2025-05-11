package GUI.Component;

import java.lang.reflect.Method;
import java.util.*;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author phucp
 */
public class TableModel<T> extends AbstractTableModel {

    private ArrayList<T> data;
    private Vector<String> columnNames;
    private Vector<String> methodNames;

    public TableModel(List<T> data, String[] columnNames, String[] methodNames) {
        this.data = new ArrayList<>(data);
        this.columnNames = new Vector<>(List.of(columnNames));
        this.methodNames = new Vector<>(List.of(methodNames));

        // Kiểm tra số lượng tên cột và tên phương thức
        if (this.columnNames.size() != this.methodNames.size()) {
            throw new IllegalArgumentException("Số lượng tên cột và tên phương thức không khớp.");
        }
    }

    @Override
    public int getRowCount() {
        return data.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.size();
    }

    @Override
    public String getColumnName(int column) {
        return columnNames.get(column);
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        T rowData = data.get(rowIndex);
        String methodName = methodNames.get(columnIndex);

        try {
            Method method = rowData.getClass().getMethod(methodName);
            Object value = method.invoke(rowData);

            // Xử lý riêng cho getGioiTinh()
            if (methodName.equals("isGioi_Tinh") && value instanceof Boolean) {
                return ((Boolean) value) ? "Nữ" : "Nam";
            }
            if (methodName.equals("getStatus") && value instanceof Integer) {
                return ((Integer) value) == 1 ? "Hoạt động" : "Dừng";
            }
            return value;
        } catch (NoSuchMethodException | java.lang.reflect.InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Phương thức để cập nhật dữ liệu (hữu ích cho việc tái sử dụng)
    public void setData(ArrayList<T> newData) {
        this.data = newData;
        fireTableDataChanged(); // Thông báo cho JTable biết dữ liệu đã thay đổi
    }

    // Phương thức để lấy dữ liệu hiện tại (nếu cần)
    public ArrayList<T> getData() {
        return this.data;
    }

}
