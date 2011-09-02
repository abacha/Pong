#include "ETHLib/Collide.angelscript"
class Ball
{
	private ETHEntity@ entity;
	private vector2 direction;
	private vector2 initialPos;
	
	Player@ player1;
	Player@ player2;
	
	Ball(const vector2 pos, Player@ _player1, Player@ _player2)
	{
		AddEntity("ball.ent", vector3(pos, 0), @entity);
		initialPos = pos;
		entity.Scale(gScale.getScale());
		entity.SetVector2("direction", vector2(1, 1));
		entity.SetFloat("movementRatio", gTimeManager.scaledUnitsPerSecond(15.0f));
		@player1 = @_player1;
		@player2 = @_player2;
		entity.SetObject("player1", @player1);
		entity.SetObject("player2", @player2);
		entity.SetFloat("ballRadius", entity.GetSize().x / 2);
	}
	
	void update()
	{
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
	}
	
	void reset()
	{
		entity.SetPositionXY(initialPos);
	}
}

void ETHCallback_ball(ETHEntity@ entity)
{
	const vector2 pos = entity.GetPositionXY();
	const vector2 screenSize = GetScreenSize();
	const float movementRatio = entity.GetFloat("movementRatio");
	vector2 direction = entity.GetVector2("direction");
	
	if ((pos.y + entity.GetFloat("ballRadius")) > screenSize.y || (pos.y - entity.GetFloat("ballRadius")) < 0)
	{
		direction.y *= -1;
	}
	Player@ p1;
	Player@ p2;
	entity.GetObject("player1", @p1);
	entity.GetObject("player2", @p2);
	
	if (scaledCollide(p1.getCollisionBox(), entity) || scaledCollide(p2.getCollisionBox(), entity))
	{
		direction.x *= -1;
	}
		
	entity.AddToAngle(movementRatio);
	entity.AddToPositionXY(direction * movementRatio);
	entity.SetVector2("direction", direction);
}