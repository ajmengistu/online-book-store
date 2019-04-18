package com.onlinebookstore.controller;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;

public class ListStarRatings extends SimpleTagSupport {
	String averageRating = "0.0";

	public void setRating(String averageRating) {
		this.averageRating = averageRating;
	}

	public void doTag() throws JspException, IOException {
		String oneStar = "<span class='fa fa-star checked' style='color: orange;'></span>";
		String halfStar = "<span class='fa fa-star-half-full checked' style='color: orange;'></span>";
		String noStar = "<span class='fa fa-star'></span>";

		JspWriter out = getJspContext().getOut();

		if (this.averageRating != null || !this.averageRating.equals("")) {
			Float avgRating = Float.parseFloat(averageRating);
			int numOfStarsPrinted = 0;
			if (!avgRating.equals(0F)) {
				for (int i = 0; i < avgRating - 1; i++) {
					out.println(oneStar);
					numOfStarsPrinted++;
				}

				if (((float) Math.round(avgRating) - avgRating) > 0) {
					out.println(halfStar);
					numOfStarsPrinted++;
				}

				for (int i = 0; i < 5 - numOfStarsPrinted; i++) {
					out.println(noStar);
				}
			}
		}
	}
}
