import System.Environment
import System.IO  
import Control.Monad  
import System.Directory  
import Data.List  
import Data.String
import System.Exit
import ParseLib
import BruteForce

--The main method will take in command line arguments and read file into a string. 
-- to execute ./filename arg1 arg2

main = do
    args <- getArgs
    handle <- openFile (args!! 0) ReadMode
    contents <- (hGetContents handle)
    --filecontent not contails all of the contents in the file. 
    let filecontent = map noEndSpaces (lines (noCR contents))
    --putStr $ unlines filecontent
        --Now we split it into sections
        --First sections that is sliced is name
        nameSec = sliced "Name:" "forced partial assignment:" filecontent
        --Forced partial Assignment
        forcedSec = sliced "forced partial assignment:" "forbidden machine:" filecontent
        --Fobidden machines
        forbidSec = sliced "forbidden machine:" "too-near tasks:" filecontent
        --Too near Task
        tntSec = sliced "too-near tasks:" "machine penalties:" filecontent
        --Machine pentalties
        machPenSec = sliced "machine penalties:" "too-near penalities" filecontent
        --too near penalties
        tnpSec = sliced "too-near penalities" "" filecontent
    --Now that we have each part of the code sliced from section to section
    --We have to check if the spellings are correct for all the sections
    --If the spellings are incorrectly, the string tokens passed as arguments earlier won't be found 
    --this will result in a creation of an empty string
    --Here we will check if the strings are empty or not
    --this returns "Error while parsing file" if the file does not have the correct input
    if (elem 0 (map length [nameSec, forcedSec, forcedSec, tnpSec, machPenSec, tnpSec]))
    then do writeFile (last args) "Error while parsing input file"
            exitSuccess
    else return ()
    --We have now checked for errors in spellings
    --Now we want to check for errors with inputs
    --First, lets check if there is error under name.
    if ((length(delWhitespaceLines nameSec)/=1))
    then do writeFile (last args) "Error while parsing input file"
            exitSuccess
    else return()
    --After name, we want to formats of the rest.
    --First we need to ensure the input is a 2-tuple.
    if(anding (map twotup(delWhitespaceLines forcedSec)))
    then return()
    else do writeFile (last args) "invalid machine/task - not a 2-tuple forced"
            exitSuccess
 
    --Now we can check that forced assignments is actually a valid machine and task pair
    if (anding(map validMTPair(delWhitespaceLines forcedSec)))
    then return()
    else do writeFile (last args) "Invalid Machine/Task pair - 1"
            exitSuccess

    --Ensure forbidden machines are a 2-tuple.
    if(anding (map twotup(delWhitespaceLines forbidSec)))
    then return()
    else do writeFile (last args) "invalid machine/task - not a 2-tuple forbid"
            exitSuccess

    --Ensure too near task is a 2-tuple
    if(anding (map twotup(delWhitespaceLines tntSec)))
    then return()
    else do writeFile (last args) "invalid machine/task - not a 2-tuple too near task"
            exitSuccess

    --Ensures too near penalty is a 3-tuple
    if(anding (map tuple3(delWhitespaceLines tnpSec)))
    then return()
    else do writeFile (last args) "invalid machine/task - not a 3-tuple too near p"
            exitSuccess

    --Now we can check that forbidden assignments is actually a valid machine and task pair
    if (anding(map validMTPair(delWhitespaceLines forbidSec)))
    then return()
    else do writeFile (last args) "Invalid Machine/Task pair - 2"
            exitSuccess

    --Now we check if the too near task is valid
    if (anding(map validTNTPair(delWhitespaceLines tntSec)))
    then return()
    else do writeFile (last args) "Invalid Task/Task pair"
            exitSuccess

    --Ensure that the machine penalty is vertically of height 8
    if(length(delWhitespaceLines machPenSec) /= 8)
    then do writeFile (last args) "Machine Penalty Error"
            exitSuccess 
    else return()
    
    let machPen = map (map read) (map words (delWhitespaceLines machPenSec)) :: [[Int]]
    --print machPen
    --ensures that the penalty matrix has total elements of 64
    if(sum(map length machPen)/= 64)
    then do writeFile (last args) "Machine Penalty Error"
            exitSuccess 
    else return()
    
    --Now we can check that forbidden assignments is actually a valid machine and task pair
    if (anding(map validTTtrip(delWhitespaceLines tnpSec)))
    then return()
    else do writeFile (last args) "Invalid task"
            exitSuccess
    

    --let aaa  = "BBB" ++ "CCC"
    --writeFile (last args) (unlines nameSec)
