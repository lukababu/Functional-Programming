import System.Environment
import System.IO  
import Control.Monad  
import System.Directory  
import Data.List  

--The main method will take in command line arguments and read file into a string. 
-- to execute ./mysystem arg1 arg2

main = do
    args <- getArgs
    putStrLn "The filemanes are:"
    mapM putStrLn args  
    putStrLn "The head name is:"
    putStrLn (outfile args)
    view (infile args)
    --view (tail args) This code was for test purposes only. Test: Passed

--Prints the given filename to console
view :: [String] -> IO ()  
view [fileName] = do  
    contents <- readFile fileName  
    let todoTasks = lines contents  
        numberedTasks = zipWith (\n line -> show n ++ " - " ++ line) [0..] todoTasks  
    putStr $ unlines numberedTasks

--Strips the array of argument to to find the input file
infile :: [String] -> [String]
infile [] = error "No input fileName given!"
infile (x:_) = [x]
