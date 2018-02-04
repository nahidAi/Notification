package test.notification.com.notification.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import test.notification.com.notification.R;
import test.notification.com.notification.activity.MainActivity;
import test.notification.com.notification.adapter.AdapterBigPerson;
import test.notification.com.notification.adapter.AdapterFavorite;

public class PageFragment extends Fragment {
    private int mPage;
    public static final String ARG_PAGE = "ARG_PAGE";
    RecyclerView recyclerView;
    AdapterFavorite adapterFavorite;

    public static PageFragment newInstance(int page) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        PageFragment fragment = new PageFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPage = getArguments().getInt(ARG_PAGE);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_page, container, false);
        if (mPage==2){
            recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
            AdapterBigPerson adapterBigPerson = new AdapterBigPerson(MainActivity.context);
            recyclerView.setAdapter(adapterBigPerson);
            recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.context));

        }else  if (mPage == 1){
            recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
             adapterFavorite= new AdapterFavorite(MainActivity.context);
            recyclerView.setAdapter(adapterFavorite);
            recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.context));
        }



        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (adapterFavorite !=null){
            adapterFavorite.notifyDataSetChanged();
        }
    }
}
