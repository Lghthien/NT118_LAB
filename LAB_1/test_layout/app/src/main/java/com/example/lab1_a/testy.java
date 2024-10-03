import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class testy extends AppCompatActivity {

    // Khai báo các biến LinearLayout
    private LinearLayout llNameContainer, llAddressContainer, llParentContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Gọi các hàm khởi tạo LinearLayout
        createNameContainer();
        createAddressContainer();
        createParentContainer();

        // Thiết lập llParentContainer làm giao diện chính của Activity
        setContentView(llParentContainer);
    }

    // Hàm khởi tạo LinearLayout cho thông tin tên
    private void createNameContainer() {
        llNameContainer = new LinearLayout(this);
        llNameContainer.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        ));
        llNameContainer.setOrientation(LinearLayout.HORIZONTAL);

        // Tạo TextView cho nhãn "Name"
        TextView tvName = new TextView(this);
        tvName.setText("Name: ");
        llNameContainer.addView(tvName);

        // Tạo TextView cho giá trị tên
        TextView tvNameValue = new TextView(this);
        tvNameValue.setText("John Doe");
        llNameContainer.addView(tvNameValue);
    }

    // Hàm khởi tạo LinearLayout cho thông tin địa chỉ
    private void createAddressContainer() {
        llAddressContainer = new LinearLayout(this);
        llAddressContainer.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        ));
        llAddressContainer.setOrientation(LinearLayout.HORIZONTAL);

        // Tạo TextView cho nhãn "Address"
        TextView tvAddress = new TextView(this);
        tvAddress.setText("Address: ");
        llAddressContainer.addView(tvAddress);

        // Tạo TextView cho giá trị địa chỉ
        TextView tvAddressValue = new TextView(this);
        tvAddressValue.setText("911 Hollywood Blvd");
        llAddressContainer.addView(tvAddressValue);
    }

    // Hàm khởi tạo LinearLayout chính và thêm các layout con vào đó
    private void createParentContainer() {
        llParentContainer = new LinearLayout(this);
        llParentContainer.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
        ));
        llParentContainer.setOrientation(LinearLayout.VERTICAL);

        // Thêm các container con vào container chính
        llParentContainer.addView(llNameContainer);
        llParentContainer.addView(llAddressContainer);
    }
}
