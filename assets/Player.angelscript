class Player
{
	private string name;
	private ETHEntity@ entity;
	private uint score;
	private uint index;
	
	Player(const string entityName, const vector2 pos, const uint _index)
	{
		name = entityName;
		AddEntity(entityName + ".ent", vector3(pos, 0), @entity);
		entity.Scale(gScale.getScale());
		score = 0;
		index = _index;
	}
	
	collisionBox getCollisionBox()
	{
		return gScale.getAbsoluteCollisionBox(entity);
	}
	
	void update()
	{		
		move();	
		const vector2 screenSize = GetScreenSize();
		const vector2 pos = entity.GetPositionXY();
		uint color = 0xFF000000;
		vector2 textPos = vector2(screenSize.x / 3, screenSize.y / 8);
		if (pos.x > screenSize.x / 2)
		{
			textPos.x *= 2;
			color = 0xFFFFFFFF;
		}
		drawCenteredText(textPos, "#"+score, "Verdana64.fnt", gScale.getScale(), color);
	}
	
	void move()
	{
		ETHInput@ input = GetInputHandle();
		#if TESTING			
			if (input.GetKeyState(K_UP) == KS_DOWN)
			{
				entity.AddToPositionXY(vector2(0, -10));
			}
			if (input.GetKeyState(K_DOWN) == KS_DOWN)
			{
				entity.AddToPositionXY(vector2(0, 10));
			}
		#endif

		const vector2 screenSize = GetScreenSize();
		const vector2 pos = entity.GetPositionXY();
		vector2 pos0 = input.GetTouchPos(0);
		vector2 pos1 = input.GetTouchPos(1);
		
		if (pos0.y < 0)
		{
			pos0.y = 0;
		}
		
		if (pos1.y < 0)
		{
			pos1.y = 0;
		}
		
		if (pos0.y > screenSize.y)
		{
			pos0.y = screenSize.y;
		}
		
		if (pos1.y > screenSize.y)
		{
			pos1.y = screenSize.y;
		}

		if (input.GetTouchState(0) == KS_DOWN || input.GetTouchState(1) == KS_DOWN)
		{
			if (index == 0)
			{
				if (pos0.x < screenSize.x / 2 && pos0.x != -1)
				{
					entity.SetPosition(vector3(pos.x, pos0.y, 0));
				}
				if (pos1.x < screenSize.x / 2 && pos1.x != -1)
				{
					entity.SetPosition(vector3(pos.x, pos1.y, 0));
				}
			}
			if (index == 1)
			{
				if (pos0.x > screenSize.x / 2)
				{
					entity.SetPosition(vector3(pos.x, pos0.y, 0));
				}
				if (pos1.x > screenSize.x / 2)
				{
					entity.SetPosition(vector3(pos.x, pos1.y, 0));
				}
			}
		}
	}
	
	void mahPoint()
	{
		score++;
	}
	
	int getID()
	{
		return entity.GetID();
	}
}