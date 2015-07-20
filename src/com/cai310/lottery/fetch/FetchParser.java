package com.cai310.lottery.fetch;


public interface FetchParser<T, E> {

	T fetch(E param);
}
