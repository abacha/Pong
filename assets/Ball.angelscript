#include "ETHLib/Collide.angelscript"
class Ball
{
	private ETHEntity@ entity;
	private vector2 direction;
	private float movementRatio;
	private vector2 initialPos;
	
	Player@ player1;
	Player@ player2;
	
	Ball(const vector2 pos, Player@ _player1, Player@ _player2)
	{
		AddEntity("ball.ent", vector3(pos, 0), @entity);
		initialPos = pos;
		entity.Scale(gScale.getScale());
		direction = vector2(1, 1);
		movementRatio = gTimeManager.scaledUnitsPerSecond(15.0f);
		@player1 = @_player1;
		@player2 = @_player2;
	}
	
	void update()
	{
		entity.AddToAngle(movementRatio);
		entity.AddToPositionXY(direction * movementRatio);
		const vector2 pos = entity.GetPositionXY();
		const vector2 screenSize = GetScreenSize();
		if (pos.x > screenSize.x || pos.x < 0)
		{	
			if (pos.x > screenSize.x)
			{
				player2.mahPoint();
			}
			else
			{
				player1.mahPoint();
			}
			reset();
		}
		if (pos.y > screenSize.y || pos.y < 0)
		{
			direction.y *= -1;
		}
		if (scaledCollide(player1.getCollisionBox(), entity))
		{
			direction.x *= -1;
		}
		if (scaledCollide(player2.getCollisionBox(), entity))
		{
			direction.x *= -1;
		}
	}
	
	void reset()
	{
		entity.SetPositionXY(initialPos);
	}
}