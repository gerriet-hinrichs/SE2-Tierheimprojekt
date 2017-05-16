/*
 * Application to help putting pets from animal shelter across.
 * Copyright (C) 2017
 *     Felix Koch <felix.koch@haw-hamburg.de>,
 *     Kristian Exss <Kristian.Exss@HAW-Hamburg.de>,
 *     Adrian Bostelmann <Adrian.Bostelmann@HAW-Hamburg.de>,
 *     Karsten Boehringer <Karsten.Boehringer@HAW-Hamburg.de>,
 *     Gehui Xu <Gehui.Xu@HAW-Hamburg.de>,
 *     Gerriet Hinrichs <gerriet.hinrichs@haw-hamburg.de>.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package de.stuff42.se2tierheimprojekt.services;

import java.util.List;

import de.stuff42.se2tierheimprojekt.datatypes.QuestionInterface;

public class QuestionServiceBean implements QuestionService {

  @Override
  public QuestionInterface getFirstWithAnswers() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public QuestionInterface getByIDWithAnswers(long id) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public List<QuestionInterface> getList() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public boolean create(QuestionInterface data) {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public boolean update(QuestionInterface data) {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public boolean delete(long id) {
    // TODO Auto-generated method stub
    return false;
  }

}
