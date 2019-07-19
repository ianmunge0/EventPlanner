package com.testmaps.testmaps;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import com.tokenautocomplete.TokenCompleteTextView;

import java.util.List;

public class ContactsCompletionView extends TokenCompleteTextView<Person> {
    public ContactsCompletionView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected View getViewForObject(Person person) {

        LayoutInflater l = (LayoutInflater) getContext().getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        TextView view = (TextView) l.inflate(R.layout.contact_token, (ViewGroup) getParent(), false);
        view.setText(person.getEmail());

        return view;
    }

    @Override
    protected Person defaultObject(String completionText) {
        //Oversimplified example of guessing if we have an email or not
        int index = completionText.indexOf('@');
        if (index == -1) {
            return new Person(completionText, completionText.replace(" ", "") + "@example.com");
        } else {
            return new Person(completionText.substring(0, index), completionText);
        }
    }
    @Override
    public boolean shouldIgnoreToken(Person token) {
        return getObjects().contains(token);
    }

    @Override
    public List<Person> getObjects() {
        //Toast.makeText(getContext(), getObjects().toString(), Toast.LENGTH_LONG).show();
        return super.getObjects();
    }
}