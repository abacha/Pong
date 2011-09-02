#include "ETHLib/TimeManager.angelscript"
#include "Player.angelscript"
#include "Ball.angelscript"

class Game : State
{	
	Player @player1;
	Player @player2;
	Ball ball;
	
	void start()
	{		
		LoadScene("", PRELOOP, LOOP, gScale.scale(vector2(854, 480)));
	}
	
	void preLoop()
	{
		SetBackgroundColor(0x444444);
		gScale.scaleEntities();
		const float offset = gScale.scale(100.0f);
		const vector2 screenSize = GetScreenSize();
		@player1 = Player("player1", vector2(offset, screenSize.y / 2), 0);
		@player2 = Player("player2", vector2(screenSize.x - offset, screenSize.y / 2), 1);
		ball = Ball(screenSize / 2, player1, player2);
	}
	
	void loop()
	{
		drawScaledSprite("sprites/bar.png", vector2(GetScreenSize().x / 2, 0), gScale.getScale());
		player1.update();
		player2.update();
		ball.update();
		handleBackButton();
	}
			
	string getName()
	{
		return "game";
	}	
		
	void handleBackButton()
	{
		ETHInput@ input = GetInputHandle();
		if (input.GetKeyState(K_BACK) == KS_HIT)
		{
			Exit();
		}
	}
}