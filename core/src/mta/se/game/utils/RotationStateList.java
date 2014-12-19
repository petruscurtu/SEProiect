package mta.se.game.utils;

//list of all rotation states for each piece

public enum RotationStateList {
		I(new int[][][]{
		{
			{0,0,0,0},
			{0,0,0,0},
			{1,1,1,1},
			{0,0,0,0},
		},
		{
			{0,0,1,0},
			{0,0,1,0},
			{0,0,1,0},
			{0,0,1,0}
		},
		{
			{0,0,0,0},
			{1,1,1,1},
			{0,0,0,0},
			{0,0,0,0}
		},
		{
			{0,1,0,0},
			{0,1,0,0},
			{0,1,0,0},
			{0,1,0,0}
		}
		}),
		//---------------
		O(new int[][][]{
		{
				{0,0,0,0},
				{0,1,1,0},
				{0,1,1,0},
				
		}}),
		//---------------	
		T(new int[][][]{
				{
					{0,0,0},
					{1,1,1},
					{0,1,0},
					
				},
				{
					{0,1,0},
					{0,1,1},
					{0,1,0},
				},
				{					
					{0,1,0},
					{1,1,1},
					{0,0,0},
				},
				{
					{0,1,0},
					{1,1,0},
					{0,1,0},
				},
		}),
			//---------------
		S(new int[][][]{
				{
					{0,0,0},
					{1,1,0},
					{0,1,1},
				},
				{
					{0,0,1},
					{0,1,1},
					{0,1,0}
				},
				{
					{1,1,0},
					{0,1,1},
					{0,0,0}
				},
				{
					{0,1,0},
					{1,1,0},
					{1,0,0}
				}
			}), 
		//---------------
		Z(new int[][][]{
				{
					{0,0,0},
					{0,1,1},
					{1,1,0}
				},
				{
					{0,1,0},
					{0,1,1},
					{0,0,1}
				},
				{
					{0,1,1},
					{1,1,0},
					{0,0,0}
				},
				{
					{1,0,0},
					{1,1,0},
					{0,1,0}
				}
		}), 
		//---------------
		J(new int[][][]{
				{
					{0,0,0},
					{1,1,1},
					{1,0,0},
				},
				{
					{0,1,0},
					{0,1,0},
					{0,1,1}
				},
				{
					{0,0,1},
					{1,1,1},
					{0,0,0}
				},
				{
					{1,1,0},
					{0,1,0},
					{0,1,0}
				},
		}),
		//---------------
		L(new int[][][]{
				{
					{0,0,0},
					{1,1,1},
					{0,0,1},
				},
				{
					{0,1,1},
					{0,1,0},
					{0,1,0}
				},
				{
					{1,0,0},
					{1,1,1},
					{0,0,0}
				},
				{
					{0,1,0},
					{0,1,0},
					{1,1,0}
				},
		});
		//---------------
		
		int[][][] stateList;
		
		int[][] getRotationState(int state){
			return stateList[state];
		}
		
		int[][][] getRotationStates(){
			return stateList;
		}
		
		int getStateCount(){
			return stateList.length;
		}
		
		private RotationStateList(int[][][] statelist) {
			stateList = statelist;
		}	
		/*
		final int[][][] SHAPES = {
				{
					{0,0,0,0},
					{1,1,1,1},
					{0,0,0,0},
					{0,0,0,0},
				},	
				{
					{1,1},
					{1,1},
				},	
				{
					{0,1,0},
					{1,1,1},
					{0,0,0},
				},
				{
					{0,1,1},
					{1,1,0},
					{0,0,0},
				},
				{
					{1,1,0},
					{0,1,1},
					{0,0,0},
				},
				{
					{1,0,0},
					{1,1,1},
					{0,0,0},
				},
				{
					{0,0,1},
					{1,1,1},
					{0,0,0},
				},
		};
		
		*/
	
}
