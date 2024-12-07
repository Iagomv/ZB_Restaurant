package UI.Prestamos;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.SqlDateModel;
import javax.swing.*;
import java.text.SimpleDateFormat;
import java.util.Properties;

public class CalendarioUtil {

    // Método para crear un JDatePicker configurado
    public static JDatePickerImpl crearDatePicker() {
        // Modelo de fecha para el selector de fecha
        SqlDateModel model = new SqlDateModel();
        model.setSelected(true); // Establecer la fecha actual por defecto

        // Propiedades del calendario
        Properties p = new Properties();
        p.put("text.today", "Hoy");
        p.put("text.month", "Mes");
        p.put("text.year", "Año");

        // Crear el panel de fecha y el picker
        JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
        return new JDatePickerImpl(datePanel, new DateLabelFormatter());
    }

    // Clase formateadora de la fecha seleccionada
    public static class DateLabelFormatter extends JFormattedTextField.AbstractFormatter {
        private String datePattern = "yyyy-MM-dd"; // Cambia el formato si es necesario
        private SimpleDateFormat dateFormatter = new SimpleDateFormat(datePattern);

        @Override
        public Object stringToValue(String text) throws java.text.ParseException {
            return dateFormatter.parseObject(text);
        }

        @Override
        public String valueToString(Object value) {
            if (value != null) {
                java.util.Calendar cal = (java.util.Calendar) value;
                return dateFormatter.format(cal.getTime());
            }
            return "";
        }
    }

    // Método para obtener la fecha seleccionada como java.sql.Date
    public static java.sql.Date obtenerFechaSeleccionada(JDatePickerImpl datePicker) {
        java.util.Date selectedDate = (java.util.Date) datePicker.getModel().getValue();
        if (selectedDate != null) {
            return new java.sql.Date(selectedDate.getTime()); // Convertir java.util.Date a java.sql.Date
        }
        return null;
    }
}
