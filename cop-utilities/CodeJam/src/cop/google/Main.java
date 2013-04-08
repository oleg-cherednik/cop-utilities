package cop.google;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Main
{
	private Data[] results;

	public static void main(String[] args)
	{
		try
		{
			System.out.println("Start small...");
			new Main().run("A-small-attempt2");
			System.out.println("done");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	private void run(String fileName) throws IOException, InterruptedException
	{
		BufferedReader in = new BufferedReader(new FileReader(new File(fileName + ".in")));
		int T = Integer.parseInt(in.readLine().trim());
		Data.total = T;
		results = new Data[T];

		for(int i = 0; i < T; i++)
			runCalculationThread(results[i] = new Data(in), i + 1);

		in.close();

		while(Data.total > 0)
		{}

		writeData(fileName);
	}

	private void runCalculationThread(final Data data, final int i)
	{
//		new Thread("Case " + i)
//		{
//			@Override
//			public void run()
//			{
//				System.out.println("Start case " + i);
				data.proceed();
//				System.out.println("------------------ stop case " + i + " (more " + Data.total + ")");
//			}
//		}.start();
	}

	private void writeData(String fileName) throws IOException
	{
		BufferedWriter out = new BufferedWriter(new FileWriter(new File(fileName + ".out")));

		for(int i = 0; i < results.length; i++)
		{
			if(i > 0)
				out.write("\n");

			out.write("Case #" + (i + 1) + ": " + results[i].time);
		}

		out.close();
	}
}

class Data
{
	private final Robot orangeRobot;
	private final Robot blueRobot;
	private final List<Hallway> pressQueue = new ArrayList<Hallway>();
	public int time;

	volatile static int total;

	public Data(BufferedReader in) throws IOException
	{
		List<Integer> orangeButtons = new ArrayList<Integer>();
		List<Integer> blueButtons = new ArrayList<Integer>();
		String[] ss = in.readLine().split(" ");
		int buttonsAmount = Integer.parseInt(ss[0].trim());
		List<Integer> buttons;

		for(int i = 0, size = buttonsAmount * 2; i < size;)
		{
			switch(Hallway.parseSymbol(ss[++i].charAt(0)))
			{
			case ORANGE:
				pressQueue.add(Hallway.ORANGE);
				buttons = orangeButtons;
				break;
			case BLUE:
				pressQueue.add(Hallway.BLUE);
				buttons = blueButtons;
				break;
			default:
				throw new IllegalArgumentException();
			}

			buttons.add(Integer.parseInt(ss[++i].trim()));
		}
		
		orangeRobot = new Robot(Hallway.ORANGE, orangeButtons.toArray(new Integer[orangeButtons.size()]));
		blueRobot = new Robot(Hallway.BLUE, blueButtons.toArray(new Integer[blueButtons.size()]));
	}

	public void proceed()
	{
		int i = 0;
		Hallway firstPress = pressQueue.get(i++);
		Hallway nextPress = firstPress;

		while(!orangeRobot.isAllButtonsPressed() || !blueRobot.isAllButtonsPressed())
		{
			time++;

			if(firstPress == Hallway.ORANGE)
			{
				System.out.print("O: ");
				orangeRobot.nextStep(nextPress == Hallway.ORANGE);

				if(orangeRobot.isButtonJustPressed())
				{
					if(i >= pressQueue.size())
						break;
					nextPress = pressQueue.get(i++);
				}

				System.out.print("B: ");
				blueRobot.nextStep(nextPress == Hallway.BLUE);

				if(blueRobot.isButtonJustPressed())
				{
					if(i >= pressQueue.size())
						break;

					nextPress = pressQueue.get(i++);
				}
			}
			else
			{
				System.out.print("B: ");
				blueRobot.nextStep(nextPress == Hallway.BLUE);

				if(blueRobot.isButtonJustPressed())
				{
					if(i >= pressQueue.size())
						break;
					nextPress = pressQueue.get(i++);
				}

				System.out.print("O: ");
				orangeRobot.nextStep(nextPress == Hallway.ORANGE);

				if(orangeRobot.isButtonJustPressed())
				{
					if(i >= pressQueue.size())
						break;

					nextPress = pressQueue.get(i++);
				}
			}

			System.out.println();
		}

		System.out.println("time: " + time);

		total--;
	}
}

enum Hallway
{
	ORANGE('O'),
	BLUE('B');

	private final char symbol;

	Hallway(char symbol)
	{
		this.symbol = symbol;
	}

	public static Hallway parseSymbol(char symbol)
	{
		if(ORANGE.symbol == symbol)
			return ORANGE;
		if(BLUE.symbol == symbol)
			return BLUE;

		throw new IllegalArgumentException();
	}
}

class Robot
{
	private final Hallway hallway;
	private final Integer[] buttons;
	private int i;
	private int button = 1;
	private boolean allButtonsPressed;
	private boolean buttonJustPressed;

	public Robot(Hallway hallway, Integer[] buttons)
	{
		this.hallway = hallway;
		this.buttons = buttons;

		if(buttons.length == 0)
			return;

		for(int i = buttons.length - 1; i > 0; i--)
			buttons[i] -= buttons[i - 1];

		if(buttons[0] > 0)
			buttons[0]--;
		else
			buttons[0]++;
	}

	private void pushButton()
	{
		if(allButtonsPressed || buttons[i] != 0)
			throw new IllegalStateException();

		buttons[i++] = null;
		System.out.println("Push button " + button);
		buttonJustPressed = true;
	}

	private void nextStep()
	{
		if(allButtonsPressed)
			throw new IllegalStateException();

		if(i >= buttons.length || buttons[i] == 0 || buttons[i] == null)
			System.out.println("Stay at button " + button);
		else
		{
			if(buttons[i] > 0)
			{
				buttons[i]--;
				button++;
			}
			else
			{
				buttons[i]++;
				button--;
			}

			System.out.println("Move to button " + button);
		}

		buttonJustPressed = false;
	}

	public void pushOrWait()
	{
		if(isReadyPushButton())
			pushButton();
		else
			nextStep();
	}

	public void nextStep(boolean pushAvailable)
	{
		if(pushAvailable)
			pushOrWait();
		else
			nextStep();
	}

	private boolean isReadyPushButton()
	{
		return buttons[i] == 0;
	}

	public boolean isAllButtonsPressed()
	{
		return allButtonsPressed;
	}

	public boolean isButtonJustPressed()
	{
		return buttonJustPressed;
	}

	public String toString()
	{
		return "" + hallway;
	}
}
