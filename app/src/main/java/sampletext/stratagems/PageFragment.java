package sampletext.stratagems;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class PageFragment extends Fragment {

    DisplayDataUnit _unit;

    int             _pageIndex;

    TextView _cardHeader, _cardHeaderContent, _functionHeader, _functionContent, _effectHeader, _effectContent, _dialogHeader, _dialogContent;

    static PageFragment newInstance(int page) {
        PageFragment pageFragment = new PageFragment();
        pageFragment._pageIndex = page;
        return pageFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        _unit = DataUnitsHolder.get(_pageIndex);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.pager_fragment, null);

        _cardHeader = view.findViewById(R.id.cardHeader);
        _cardHeaderContent = view.findViewById(R.id.cardHeaderContent);
        _functionHeader = view.findViewById(R.id.functionHeader);
        _functionContent = view.findViewById(R.id.functionContent);
        _effectHeader = view.findViewById(R.id.effectHeader);
        _effectContent = view.findViewById(R.id.effectContent);
        _dialogHeader = view.findViewById(R.id.dialogHeader);
        _dialogContent = view.findViewById(R.id.dialogContent);

        if (Static.DiagonalInches >= 6.5) {
            _cardHeader.setTextSize(32);
            _cardHeaderContent.setTextSize(28);
            _functionHeader.setTextSize(24);
            _functionContent.setTextSize(20);
            _effectHeader.setTextSize(24);
            _effectContent.setTextSize(20);
            _dialogHeader.setTextSize(24);
            _dialogContent.setTextSize(20);
        }
        else {
            _cardHeader.setTextSize(16);
            _cardHeaderContent.setTextSize(14);
            _functionHeader.setTextSize(16);
            _functionContent.setTextSize(14);
            _effectHeader.setTextSize(16);
            _effectContent.setTextSize(14);
            _dialogHeader.setTextSize(16);
            _dialogContent.setTextSize(14);
        }

        _cardHeader.setText(String.format("Стратагема %s", String.valueOf(_pageIndex + 1)));
        _cardHeaderContent.setText(_unit.get_cardHeader());
        _functionContent.setText(_unit.get_function());
        _effectContent.setText(_unit.get_effect());
        _dialogContent.setText(_unit.get_dialog());
        //_backText.setText(getResources().getString(R.string.lorem_ipsum));

        return view;
    }
}
