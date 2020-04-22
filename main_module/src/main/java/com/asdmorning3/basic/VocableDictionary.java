package com.asdmorning3.basic;

import com.asdmorning3.test.InterfaceLanguages;

import java.awt.*;
import java.io.*;
import java.util.*;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class VocableDictionary implements Serializable {

	public VocableDictionary()
	{
		vocableList = new HashSet<>();
		tagsList = new ArrayList<Tags>();
	}

	private HashSet<Vocable> vocableList;

	private ArrayList<Tags> tagsList;

	public HashSet<Vocable> getVocableList()
	{
		return vocableList;
	}

	public List<Vocable> findVocable(String word, Vocable.Language language)
	{
		try{
			return vocableList.stream().filter(
					(vocable) -> (vocable.getWord().equals(word) && vocable.getLanguage().equals(language)))
					.collect(Collectors.toList());
		}
		catch (NullPointerException e)
		{
			return new ArrayList<>();
		}
	}

	public void save() throws IOException
	{
		ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(System.getProperty("user.dir") + "/dictionary.save"));
		oos.writeObject(vocableList);
		oos.close();
	}

	public void save(String path) throws IOException
	{
		ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(path));
		oos.writeObject(vocableList);
		oos.writeObject(tagsList);
		oos.close();
	}

	public void load(String path) throws IOException, ClassNotFoundException
	{
		ObjectInputStream ois = new ObjectInputStream(new FileInputStream(path));
		vocableList = (HashSet<Vocable>) ois.readObject();
		tagsList = (ArrayList<Tags>) ois.readObject();
	}

	public void load() throws IOException, ClassNotFoundException
	{
		try {
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream(System.getProperty("user.dir") + "/dictionary.save"));
			vocableList = (HashSet<Vocable>) ois.readObject();
		}
		catch (FileNotFoundException ex)
		{
			System.out.println("File not Found");
			System.out.println(ex.toString());
		}
	}

	public List<Vocable> getAllFromLanguage(Vocable.Language lang)
	{
		List<Vocable> vocables = new ArrayList<>();
		for(Vocable v : vocableList)
		{
			if(v.getLanguage() == lang)
				vocables.add(v);
		}
		return vocables;
	}

	public boolean exists(Vocable v)
	{
		for(Vocable d : getAllFromLanguage(v.getLanguage()))
			if(Objects.equals(d.getWord(), v.getWord()))
				return true;
		return false;
	}

	public void addVocable(Vocable ... vocables)
	{
		for (Vocable vocable: vocables)
		{
			for (Vocable translation: vocables)
			{
				if (vocable.getLanguage() != translation.getLanguage())
				{
					try{
						if (!vocable.getTranslation(translation.getLanguage()).getWord().equals(translation.getWord()))
						{
							throw new NullPointerException();
						}
					}
					catch (NullPointerException e)
					{
						try{
							vocable.addTranslation(translation);
						}
						catch (IllegalArgumentException ex)
						{
							System.out.println("This should never happen.");
						}
					}
				}
				else if(!exists(vocable))
				{
					vocableList.addAll(Arrays.asList(vocables));
				}
			}
		}

		vocableList.addAll(Arrays.asList(vocables));
	}

	public Tags createTag(String description, Color color)
	{
		Tags tag = getTagByDescription(description);
		if(tag != null)
			return tag;

		tag = new Tags(description, color);
		tagsList.add(tag);
		return tag;
	}

	public Tags getTagByDescription(String description)
	{
		for(Tags tag : tagsList)
		{
			if(tag.getDescription().equals(description))
				return tag;
		}

		return null;
	}

	public ArrayList<Tags> getTagsList()
	{
		return tagsList;
	}

	public void addTagToVocable(Tags tag, Vocable vocable)
	{
		boolean addSuccess = vocable.addTag(tag);
	}

	public void removeTagToVocable(Tags tag, Vocable vocable)
	{
		boolean removeSuccess = vocable.removeTag(tag);
	}

	}
