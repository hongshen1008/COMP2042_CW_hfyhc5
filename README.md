# Refactor Event 

Overall rearranged code structure (shorter method in the bottom) and deleted extra space character.

#### MVC design pattern
1. Divided package into Model, View, Controller and MainClass.
2. Extract KeyEvent and MouseEvent from DebugPanel, GameBoard and HomeMenu classes into each of the Controller class.

#### Ball class
1. Encapsulate fields (up, down left, right).
2. Removed Point2D setlocation() method and directly set location during instantiation. (line 43-46)
3. Extract duplicated operation from move() and moveTo() method into antoher method called getCenter() (line 88).

#### Brick class
1. Removed MIN_CRACK variable in Brick class. (line 16)
2. Removed unused variable "name" in Brick class and in all its children class.
3. Added access modifier, protected Shape brickFace (line 24)
4. Renamed method "getBrick()" to "getBrickFace()" (line 68)
5. Removed variable "rnd" and its instantiation. Initialise in Crack class.
6. Extract Crack class to an independent class.

#### Crack class
1. Remove "CRACK_SECTIONS" and "JUMP_PROBABILITY" variable.
2. Remove unused inMiddle() and jumps() methods.

#### Player class
1. Changed "moveAmount" variable initialisation to call stop() method. (line 55) 
2. Renamed movRight() method to moveRight(). (line 118)
3. Encapsulate field "moveAmount". (line 144, 153)

#### Wall class
1. New method "initialiseSpeed()" to fix ball speed in every levels.(line 160)
2. Encapsulate fields "bricks, ball, player". (line 344, 353, 362)
3. Instantiate RubberBall class in initialiseSpeed(). (line 161)
4. In ballReset() method, fix ball speed instead of random speed. (line 171)
5. Removed rnd variable and operations in constructor and ballReset() method.
6. Extract levels operations to the new Levels class. 
7. Removed variables "LEVEL_COUNT, CLAY, STEEL, CEMENT" and moved to Levels class.
8. Added composition relationship, "private Levels level" variable and instantiation.
9. Renamed "private Brick[][] level" variable to "brick_level" (line 40).
10. Renamed "private int level" variable to "tmp_level" (line 41)
11. In impactWall() method, changed Crack.RIGHT to Crack.LEFT and vice versa. (line 135 & 138)

#### Levels class
1. Rename variable "brickOnLine" to "brickPerline".
 
#### DebugConsole
1. Instantiate DebugPanelController class.

#### DebugPanel class
1. Encapsulate fields "ballXSpeed, ballYSpeed". (line 151, 160)
2. Extract action events to DebugPanelController class.

#### GameBoard class
1. Instantiate GameBoardController class.
2. Moved the gameTimer event out from constructor to a new method promptMessage(). (line 114)
3. Encapsulate fields. (line 404 - end) 

#### HomeMenu class
1. Renamed variable "MENU_TEXT, menuButton, menuClicked" to "EXIT_TEXT, exitButton, exitClicked". 
2. Removed constructor parameter "owner". (line 78)
3. Removed "borderstoke" operations in constructor and drawContainer(Graphics2D g2d) method.
4. Renamed variable "txtReact" to "startTxtReact, exitTxtReact".
5. Extract repaint() method from MouseEvent's methods to new Repaint() method. (line 329)
6. Encapsulate fields. (line 302 - end)

# Addition

#### Wall class
1. Added score variable, scoreReset() method, and encapsulate "score".
2. Added getHighScore() and checkScore() method.

#### Level class
1. Additional level with three types of brick.

#### TitaniumBrick class
1. New TitaniumBrick class.

#### GameBoard class
1. Added message font and moved the messages to upper left corner.
2. Added Guide button in PauseMenu.
3. Reward and penalty in promptMessage() method.

#### HomeMenu class
1. Added background image.
2. Added Guide button in HomeMenu.

#### GuideFrame class
1. Added new GuideFrame class and "back" button.
