#include "ETHLib/eth_util.angelscript"
#include "ETHLib/State.angelscript"
#include "ETHLib/StateManager.angelscript"
#include "ETHLib/GlobalScale.angelscript"
#include "Game.angelscript"

void main()
{	
	SetPositionRoundUp(false);
	gStateManager.setState(Game());
}

const string LOOP = "loop";
const string PRELOOP = "preLoop";

void loop()
{
	gStateManager.runCurrentLoopFunction();	
}

void preLoop()
{	
	gStateManager.runCurrentPreLoopFunction();
	SetZBuffer(false);
	SetFastGarbageCollector(false);

}