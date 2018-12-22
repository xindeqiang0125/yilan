package com.xindq.yilan.fragment.search;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;

import com.xindq.yilan.R;
import com.xindq.yilan.dialog.item.ItemDialog;
import com.xindq.yilan.dialog.item.ItemDialogBtnListener;
import com.xindq.yilan.domain.Item;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

public class SearchFragment extends Fragment implements SearchPresenter.CallBack {
    private static final String TAG = "SearchFragment";
    private SearchView searchView;
    private ListView listView;
    private SearchPresenter presenter;

    private ItemDialog dialog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        searchView = view.findViewById(R.id.item_search_view);
        listView = view.findViewById(R.id.lv_item);
        searchView.setOnQueryTextListener(new QueryTextListener());
        listView.setOnItemClickListener(new ItemListListener());
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

        @Override
        public boolean onQueryTextSubmit(String query) {
            try {
                String encode = URLEncoder.encode(query, "utf-8");
                presenter.searchItems(encode);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            return false;
        }

        @Override
        public boolean onQueryTextChange(String newText) {
            return false;
        }
    }

    class ItemListListener implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            ItemListViewAdapter adapter = (ItemListViewAdapter) parent.getAdapter();
            Item item = (Item) adapter.getItem(position);
            dialog = new ItemDialog(getActivity());
            dialog.setItem(item)
                    .setOnClickBtnListener(new ItemDialogBtnListener(getActivity(),dialog))
                    .show();
        }
    }
}
