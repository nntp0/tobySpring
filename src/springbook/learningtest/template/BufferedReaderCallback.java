package springbook.learningtest.template;

import java.io.BufferedReader;
import java.io.IOException;

public interface BufferedReaderCallback {
	public int calc(BufferedReader br, int initVal) throws IOException;
}