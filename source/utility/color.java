package utility;

public enum color
{
	reset
	{
		public String toString()
		{
			return "\u001B[0m";
		}
	},
	black
	{
		public String toString()
		{
			return "\u001B[30m";
		}
	},
	red
	{
		public String toString()
		{
			return "\u001B[31m";
		}
	},
	green
	{
		public String toString()
		{
			return "\u001B[32m";
		}
	},
	yellow
	{
		public String toString()
		{
			return "\u001B[33m";
		}
	},
	blue
	{
		public String toString()
		{
			return "\u001B[34m";
		}
	},
	purple
	{
		public String toString()
		{
			return "\u001B[35m";
		}
	},
	cyan
	{
		public String toString()
		{
			return "\u001B[36m";
		}
	},
	white
	{
		public String toString()
		{
			return "\u001B[37m";
		}
	}
}
