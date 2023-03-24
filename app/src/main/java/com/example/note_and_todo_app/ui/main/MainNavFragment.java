package com.example.note_and_todo_app.ui.main;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;
import com.example.note_and_todo_app.R;
import com.example.note_and_todo_app.database.note.NoteViewModel;
import com.example.note_and_todo_app.databinding.FragmentMainNavBinding;
import com.example.note_and_todo_app.ui.note.NoteFragment;
import com.example.note_and_todo_app.ui.setting.SettingFragment;
import com.example.note_and_todo_app.ui.task.category.TaskCategoryFragment;
import com.google.android.material.navigation.NavigationBarView;
import org.jetbrains.annotations.NotNull;

public class MainNavFragment extends Fragment {

	private TaskCategoryFragment mTaskCategoryFragment;
	private NoteFragment mNoteFragment;
	private SettingFragment mSettingFragment;

	FragmentMainNavBinding mainNavBinding;
	private NoteViewModel noteViewModel;

	MutableLiveData<String> titleAppName = new MutableLiveData<>();

	@SuppressLint("NonConstantResourceId")
	NavigationBarView.OnItemSelectedListener mOnNavigationItemSelectedListener = item -> {
		switch (item.getItemId()) {
			case R.id.menuTask:
				mainNavBinding.viewPager.setCurrentItem(0);
				titleAppName.postValue(getString(R.string.tasks));
				return true;
			case R.id.menuNote:
				mainNavBinding.viewPager.setCurrentItem(1);
				titleAppName.postValue(getString(R.string.notes));
				return true;
			case R.id.menuSetting:
				mainNavBinding.viewPager.setCurrentItem(2);
				titleAppName.postValue(getString(R.string.settings));
				return true;
			default:
				return false;
		}
	};

	@Nullable
	@org.jetbrains.annotations.Nullable
	@Override
	public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
		mainNavBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_main_nav, container, false);
		return mainNavBinding.getRoot();
	}

	@Override
	public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		setupListener();
		setupBottomNavigation();
		initiateView();
	}

	private void setupBottomNavigation() {
		mainNavBinding.bottomNavigation.setItemIconTintList(null);
	}

	private void initiateView() {
		titleAppName.observe(getViewLifecycleOwner(), s -> mainNavBinding.toolbar.title.setText(s));

		ScreenSlidePagerAdapter pagerAdapter = new ScreenSlidePagerAdapter(requireActivity());
		mainNavBinding.viewPager.setAdapter(pagerAdapter);
		mainNavBinding.viewPager.setOffscreenPageLimit(2);
		mainNavBinding.bottomNavigation.setOnItemSelectedListener(mOnNavigationItemSelectedListener);

		mainNavBinding.viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
			@Override
			public void onPageSelected(int position) {
				super.onPageSelected(position);
				switch (position) {
					case 0:
						mainNavBinding.bottomNavigation.getMenu().findItem(R.id.menuTask).setChecked(true);
						titleAppName.postValue(getString(R.string.tasks));
						break;
					case 1:
						mainNavBinding.bottomNavigation.getMenu().findItem(R.id.menuNote).setChecked(true);
						titleAppName.postValue(getString(R.string.notes));
						break;
					case 2:
						mainNavBinding.bottomNavigation.getMenu().findItem(R.id.menuSetting).setChecked(true);
						titleAppName.postValue(getString(R.string.settings));
						break;
				}
			}
		});

	}
	void setupListener(){
		TaskCategoryFragment.isSearching.observe(getViewLifecycleOwner(), b -> {
			if (b) {
				mainNavBinding.toolbar.icLeft.setImageResource(R.drawable.ic_back);
			} else {
				mainNavBinding.toolbar.icLeft.setImageResource(R.drawable.ic_home);
			}
		});
		noteViewModel =  new ViewModelProvider((ViewModelStoreOwner) requireContext()).get(NoteViewModel.class);
		PopupMenu popupMenu = new PopupMenu(requireContext(),mainNavBinding.toolbar.icRight);
		popupMenu.getMenuInflater().inflate(R.menu.menu_note,popupMenu.getMenu());

		mainNavBinding.toolbar.icLeft.setOnClickListener(v -> {
			if (Boolean.TRUE.equals(TaskCategoryFragment.isSearching.getValue())) {
				TaskCategoryFragment.isSearching.postValue(false);
			}
		});

		OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
			@Override
			public void handleOnBackPressed() {
				mainNavBinding.toolbar.icLeft.performClick();
			}
		};
		requireActivity().getOnBackPressedDispatcher().addCallback((LifecycleOwner) requireContext(), callback);


		popupMenu.setOnMenuItemClickListener(item -> {
			if(mainNavBinding.viewPager.getCurrentItem() == 1){
				deleteNote(getContext());
			}

			return false;
		});

			mainNavBinding.toolbar.icRight.setOnClickListener(v -> {
				if(mainNavBinding.viewPager.getCurrentItem() == 1){
				popupMenu.show();
				}
			});

	}
	@SuppressWarnings("deprecation")
	private void deleteNote(Context context) {
		new AlertDialog.Builder(context)
				.setTitle("Delete Note")
				.setMessage("Are you sure you want to delete this note?")
				.setPositiveButton(android.R.string.yes, (dialog, which) -> noteViewModel.deleteAll())
				.setNegativeButton(android.R.string.no, null)
				.setIcon(android.R.drawable.ic_menu_delete)
				.show();

	}

	@Override
	public void onMultiWindowModeChanged(boolean isInMultiWindowMode) {
		super.onMultiWindowModeChanged(isInMultiWindowMode);

	}

	private class ScreenSlidePagerAdapter extends FragmentStateAdapter {

		public ScreenSlidePagerAdapter(@NonNull @NotNull FragmentActivity fragmentActivity) {
			super(fragmentActivity);
		}

		@NonNull @NotNull @Override
		public Fragment createFragment(int position) {
			return handleOnNavigationItemSelected(position);
		}

		@Override
		public int getItemCount() {
			return 3;
		}
	}

	private Fragment handleOnNavigationItemSelected(int position) {
		switch (position) {
			case 0: return getFragmentForIndex(0);
			case 1: return getFragmentForIndex(1);
			case 2: return getFragmentForIndex(2);
		}
		return getFragmentForIndex(0);
	}

	private Fragment getFragmentForIndex(int index) {
		switch (index) {
			case 0: return mTaskCategoryFragment != null ? mTaskCategoryFragment : initFragmentAt(index);
			case 1: return mNoteFragment != null ? mNoteFragment : initFragmentAt(index);
			case 2: return mSettingFragment != null ? mSettingFragment : initFragmentAt(index);
		}
		return mTaskCategoryFragment != null ? mTaskCategoryFragment : initFragmentAt(index);
	}

	private Fragment initFragmentAt(int position) {
		switch (position) {
			case 0:
				mTaskCategoryFragment = TaskCategoryFragment.newInstance();
				break;
			case 1:
				mNoteFragment = NoteFragment.newInstance();
				break;
			case 2:
				mSettingFragment = SettingFragment.newInstance();
				break;
		}
		return handleOnNavigationItemSelected(position);
	}
}
