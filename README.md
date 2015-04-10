# sml
User review prediction

To start first install git for your os. Check online for that.
But this can also be a starting point for Windows people - https://msysgit.github.io/
Download and install necessary stuffs (My opinion: Installation might suck in Windows. But using it might be easier.)
Also check for git plugins for your preferred IDE - http://eclipse.github.io/ for eclipse.

Now... Its cloning time.

Clone the project into your computer.
Cloning means that you are taking a copy of the project hosted in github into your own system - local copy.
Use the following command:

git clone https://github.com/Swebask/sml.git

This is a https cloning. Hence, whenever you do some changes and try to put that into github repository on web
it will ask for your github username and password. Give that to push.

Now to the more important concepts.
You can go into the folder and make your changes. Then go to your git interface (in my case its just the terminal) for that folder.
Check for your changes/status after current modification. Usually done using "git status"

You will see some red colored files that you just added to the project (color can vary).
Next, use following command to make these files accepted before pushing to github

git add .

This will "add" your files to local repo and make the changes ready for finalizing.
Now checking git status will give files in green color.

Now that you have added, type/open the option in your git UI

git commit -m "Here you should put a msg for the changes you have done"

Now that prepares the whole thing for pushing to github.
If you do not get any error then go ahead and pull other's changes in the repository

git pull --rebase origin master

This will get others's changes into your system and your change will be there too above these changes. 
If you get any error while doing above step it means there's a problem. Call those who know to solve it or GOOGLE it.

Assuming it all went smooth

git push origin master

And you are done pushing your changes. 

P.S. I know this will be quite hard to comprehend. You can find more tutorials online.
Maybe next meet I will tell basics. For now go over this link:

http://rogerdudler.github.io/git-guide/