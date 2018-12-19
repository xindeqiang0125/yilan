package com.xindq.yilan.fragment.search;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.SearchView;

import com.xindq.yilan.R;
import com.xindq.yilan.domain.Item;

import java.util.List;

public class SearchFragment extends Fragment implements SearchPresenter.CallBack {
    private static final String TAG = "SearchFragment";
    private SearchView searchView;
    private ListView listView;
    private SearchPresenter presenter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        searchView = view.findViewById(R.id.item_search_view);
        listView = view.findViewById(R.id.lv_item);
        searchView.setOnQueryTextListener(new QueryTextListener());
        presenter = new SearchPresenter(getActivity(),this);
        return view;
    }

    @Override
    public void onItemsSearched(List<Item> items) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ItemListViewAdapter adapter = new ItemListViewAdapter(getActivity(), items);
                listView.setAdapter(adapter);
            }
        });
    }

    class QueryTextListener implements SearchView.OnQueryTextListener {
        Activity activity = SearchFragment.this.getActivity();

        @Override
        public boolean onQueryTextSubmit(String query) {
            presenter.searchItems(query);
            return false;
        }

        @Override
        public boolean onQueryTextChange(String newText) {
            return false;
        }
    }
}
