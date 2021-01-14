package irdnt_list_package;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ateam_app.R;
import com.google.android.material.tabs.TabLayout;

public class IrdntListFragment extends Fragment {
    TabLayout irdnt_sort_tabs;
    Fragment selectedSortTab;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_irdnt_list, container, false);

        //재료 리스트 상단 탭 (종류별, 유통기한별, 이름별)
        irdnt_sort_tabs = rootView.findViewById(R.id.irdnt_sort_tabs);
        irdnt_sort_tabs.addTab(irdnt_sort_tabs.newTab().setText("종류별"));
        irdnt_sort_tabs.addTab(irdnt_sort_tabs.newTab().setText("유통기한별"));
        irdnt_sort_tabs.addTab(irdnt_sort_tabs.newTab().setText("이름별"));

        IrdntListTypeFragment irdntListTypeFragment = new IrdntListTypeFragment();
        IrdntListDateFragment irdntListDateFragment = new IrdntListDateFragment();
        IrdntListNameFragment irdntListNameFragment = new IrdntListNameFragment();

        irdnt_sort_tabs.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();

                if (position == 0) {
                    selectedSortTab = irdntListTypeFragment;
                } else if (position == 1) {
                    selectedSortTab = irdntListDateFragment;
                } else if (position == 2) {
                    selectedSortTab = irdntListNameFragment;
                }

                getFragmentManager().beginTransaction().replace(R.id.irdnt_list_frame, selectedSortTab).commit();
            }//onTabSelected()

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }//onTabUnselected()

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }//onTabReselected()
        });//irdnt_sort_tabs.setOnTabSelectedListener()

        // Inflate the layout for this fragment
        return rootView;
    }//onCreateView()

    public static IrdntListFragment newInstance() {
        return new IrdntListFragment();
    }

}//class