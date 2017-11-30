/*
 * Copyright 2013-2017 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package Getraenkehandel.Personenmanagement;

import org.salespointframework.useraccount.Role;
import org.salespointframework.useraccount.UserAccount;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import java.util.Iterator;

@Entity
public class Person {

	private @Id @GeneratedValue long id;

	@OneToOne
	private Adresse adresse;

    private String telefonnummer;

	@OneToOne
	private UserAccount userAccount;

	@SuppressWarnings("unused")
	protected Person() {}

	public Person(UserAccount userAccount, Adresse adresse) {
	    this.userAccount = userAccount;
        this.adresse = adresse;
	}

    /**
     * userAccount deaktivieren, damit Login von Kunden nicht möglich ist
     * @return
     */
    public void deaktiviereAccount() {
	    userAccount.setEnabled(false);
    }

	public Role getRolle() {
		Iterator it = userAccount.getRoles().iterator();
		if (it.hasNext())
			return ((Role) it.next());

		return null;
	}

	public String getRolle(Boolean userfreundlicherName) {
		Iterator it = userAccount.getRoles().iterator();
		if (it.hasNext())
			return PersonenUtils.userfreundlicherRollenName((Role) it.next());

		return null;
	}

	public boolean setRolle(Role rolle) {
        userAccount.remove(getRolle());
        userAccount.add(rolle);
        return true;
    }

    public String getTelefonnummer() {
        return telefonnummer;
    }

    public void setTelefonnummer(String telefonnummer) {
        this.telefonnummer = telefonnummer;
    }

	public UserAccount getUserAccount() {
		return userAccount;
	}

    public Adresse getAdresse() {
        return adresse;
    }

	public void setAdresse(Adresse adresse) {
		adresse = adresse;
	}

	public void setUserAccount(UserAccount userAccount) {
		userAccount = userAccount;
	}

    public long getId() {
        return id;
    }

    @Override
    public String toString() {
        return "|" + id + "|" + userAccount.getFirstname() + "|" + userAccount.getLastname() + "|Rolle: " + getRolle(true) + "|Adresse: " + adresse.toString() + "|";
    }

	public boolean equals(Person p) {
    	return this.getUserAccount().equals(p.getUserAccount());
	}
}
