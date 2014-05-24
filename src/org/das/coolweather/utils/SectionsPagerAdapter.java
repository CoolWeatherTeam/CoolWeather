package org.das.coolweather.utils;

import java.util.Locale;

import org.das.coolweather.R;
import org.das.coolweather.R.string;
import org.das.coolweather.fragments.Map;
import org.das.coolweather.fragments.Search;

import android.content.Context;
import android.content.res.Resources;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {

	private Context context;
	
	public SectionsPagerAdapter(FragmentManager fm, Context context) {
		super(fm);
		this.context = context;
	}

	@Override
	public Fragment getItem(int position) {
		// getItem is called to instantiate the fragment for the given page.
		// Return a PlaceholderFragment (defined as a static inner class
		// below).
		Fragment toReturn = null;
		switch (position+1) {
		case 1:
			toReturn = Search.newInstance();
			break;
		case 2:
			toReturn = Map.newInstance();
			break;
		}
		return toReturn;
	}

	@Override
	public int getCount() {
		// Show 3 total pages.
		return 2;
	}

	@Override
	public CharSequence getPageTitle(int position) {
		Locale l = Locale.getDefault();
		switch (position) {
		case 0:
			return context.getString(R.string.title_search).toUpperCase(l);
		case 1:
			return context.getString(R.string.title_map).toUpperCase(l);
		}
		return null;
	}
}