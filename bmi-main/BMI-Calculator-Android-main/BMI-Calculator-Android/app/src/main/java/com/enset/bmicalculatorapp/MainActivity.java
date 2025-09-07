package com.enset.bmicalculatorapp; // Replace with your package name

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast; // For displaying short messages

public class MainActivity extends AppCompatActivity {

    // Declare UI elements
    private EditText etWeight;
    private EditText etHeight;
    private Button btnCalculate;
    private TextView tvResult;
    private ImageView ivBmiCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // Link to your XML layout

        // Initialize UI elements by finding them by their IDs
        etWeight = findViewById(R.id.etWeight);
        etHeight = findViewById(R.id.etHeight);
        btnCalculate = findViewById(R.id.btnCalculate);
        tvResult = findViewById(R.id.tvResult);
        ivBmiCategory = findViewById(R.id.ivBmiCategory);

        // Set a default placeholder image
        ivBmiCategory.setImageResource(R.drawable.bmi_placeholder);

        // Set an OnClickListener for the calculate button
        btnCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateBmi();
            }
        });
    }

    private void calculateBmi() {
        String weightStr = etWeight.getText().toString();
        String heightStr = etHeight.getText().toString();

        // Input validation
        if (weightStr.isEmpty() || heightStr.isEmpty()) {
            Toast.makeText(this, "Please enter both weight and height.", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            double weight = Double.parseDouble(weightStr); // Weight in kg
            double heightCm = Double.parseDouble(heightStr); // Height in cm

            // Convert height from cm to meters for BMI calculation
            double heightMeters = heightCm / 100.0;

            // BMI Formula: weight (kg) / (height (m) * height (m))
            double bmi = weight / (heightMeters * heightMeters);

            // Display the BMI result
            tvResult.setText(String.format("Your BMI: %.2f", bmi));

            // Determine BMI category and display appropriate image
            displayBmiCategory(bmi);

        } catch (NumberFormatException e) {
            Toast.makeText(this, "Invalid input. Please enter valid numbers.", Toast.LENGTH_SHORT).show();
        }
    }

    private void displayBmiCategory(double bmi) {
        // BMI Categories (based on WHO/CDC guidelines, slightly adjusted for 5 categories)
        // Underweight: < 18.5
        // Normal weight: 18.5 - 24.9
        // Overweight: 25.0 - 29.9
        // Obese (Class I): 30.0 - 34.9
        // Morbidly Obese (Class II & III): >= 35.0

        if (bmi < 18.5) {
            ivBmiCategory.setImageResource(R.drawable.underweight);
            Toast.makeText(this, "Category: Underweight", Toast.LENGTH_SHORT).show();
        } else if (bmi >= 18.5 && bmi < 25.0) {
            ivBmiCategory.setImageResource(R.drawable.normal);
            Toast.makeText(this, "Category: Normal Weight", Toast.LENGTH_SHORT).show();
        } else if (bmi >= 25.0 && bmi < 30.0) {
            ivBmiCategory.setImageResource(R.drawable.overweight);
            Toast.makeText(this, "Category: Overweight", Toast.LENGTH_SHORT).show();
        } else if (bmi >= 30.0 && bmi < 35.0) {
            ivBmiCategory.setImageResource(R.drawable.obese1);
            Toast.makeText(this, "Category: Obese", Toast.LENGTH_SHORT).show();
        } else { // bmi >= 35.0
            ivBmiCategory.setImageResource(R.drawable.obese2);
            Toast.makeText(this, "Category: Morbidly Obese", Toast.LENGTH_SHORT).show();
        }
    }
}